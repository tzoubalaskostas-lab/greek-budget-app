package gr.budget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class BudgetEditor {

    public static void main(String[] args) throws Exception {
        System.setProperty("file.encoding", "UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        InputStream expStream = BudgetEditor.class.getResourceAsStream("/expenses.json");
        List<Expense> expenses = mapper.readValue(expStream, new TypeReference<List<Expense>>() {});

        InputStream revStream = BudgetEditor.class.getResourceAsStream("/revenues.json");
        List<Revenue> revenues = mapper.readValue(revStream, new TypeReference<List<Revenue>>() {});

        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n===== ΕΡΓΑΛΕΙΟ ΕΠΕΞΕΡΓΑΣΙΑΣ ΠΡΟΥΠΟΛΟΓΙΣΜΟΥ =====");
            System.out.println("1. Επεξεργασία εξόδων");
            System.out.println("2. Επεξεργασία εσόδων");
            System.out.println("3. Εμφάνιση τρεχουσών λιστών και ισοζυγίου");
            System.out.println("0. Έξοδος");
            System.out.print("Επιλογή: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    editExpenses(expenses, scanner);
                    saveExpenses(expenses, mapper);
                    displayBalance(expenses, revenues);
                    break;
                case 2:
                    editRevenues(revenues, scanner);
                    saveRevenues(revenues, mapper);
                    displayBalance(expenses, revenues);
                    break;
                case 3:
                    showExpenses(expenses);
                    showRevenues(revenues);
                    displayBalance(expenses, revenues);
                    break;
                case 0:
                    running = false;
                    System.out.println("Έξοδος από το εργαλείο.");
                    break;
                default:
                    System.out.println("Μη έγκυρη επιλογή.");
            }
        }

        scanner.close();
    }

    private static void showExpenses(List<Expense> expenses) {
        System.out.println("\n--- ΕΞΟΔΑ ---");
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            System.out.printf("%d. %s → %.2f € (%d)\n", i + 1, e.getMinistry(), e.getAmount_eur(), e.getYear());
        }
    }

    private static void showRevenues(List<Revenue> revenues) {
        System.out.println("\n--- ΕΣΟΔΑ ---");
        for (int i = 0; i < revenues.size(); i++) {
            Revenue r = revenues.get(i);
            System.out.printf("%d. %s → %.2f € (%d)\n", i + 1, r.getIncomesource(), r.getAmount(), r.getYear());
        }
    }

    private static void editExpenses(List<Expense> expenses, Scanner scanner) {
        showExpenses(expenses);
        System.out.print("Επιλέξτε αριθμό εξόδου για αλλαγή: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        Expense e = expenses.get(index);

        System.out.print("Νέο ποσό (€) ή Enter για να μην αλλάξει: ");
        String amountStr = scanner.nextLine();
        if (!amountStr.isEmpty()) {
            e.setAmount_eur(Double.parseDouble(amountStr));
        }

        System.out.print("Νέο έτος ή Enter για να μην αλλάξει: ");
        String yearStr = scanner.nextLine();
        if (!yearStr.isEmpty()) {
            e.setYear(Integer.parseInt(yearStr));
        }

        System.out.println("Αλλαγή στα έξοδα καταχωρήθηκε.");
    }

    private static void editRevenues(List<Revenue> revenues, Scanner scanner) {
        showRevenues(revenues);
        System.out.print("Επιλέξτε αριθμό εσόδου για αλλαγή: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        Revenue r = revenues.get(index);

        System.out.print("Νέο ποσό (€) ή Enter για να μην αλλάξει: ");
        String amountStr = scanner.nextLine();
        if (!amountStr.isEmpty()) {
            r.setAmount(Double.parseDouble(amountStr));
        }

        System.out.print("Νέο έτος ή Enter για να μην αλλάξει: ");
        String yearStr = scanner.nextLine();
        if (!yearStr.isEmpty()) {
            r.setYear(Integer.parseInt(yearStr));
        }

        System.out.println(" Αλλαγή στα έσοδα καταχωρήθηκε.");
    }

    private static void saveExpenses(List<Expense> expenses, ObjectMapper mapper) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("expenses.json"), expenses);
        } catch (Exception ex) {
            System.out.println("Σφάλμα κατά την αποθήκευση των εξόδων: " + ex.getMessage());
        }
    }

    private static void saveRevenues(List<Revenue> revenues, ObjectMapper mapper) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("revenues.json"), revenues);
        } catch (Exception ex) {
            System.out.println("Σφάλμα κατά την αποθήκευση των εσόδων: " + ex.getMessage());
        }
    }

    private static void displayBalance(List<Expense> expenses, List<Revenue> revenues) {
        double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount_eur).sum();
        double totalRevenues = revenues.stream().mapToDouble(Revenue::getAmount).sum();

        System.out.printf("\n Σύνολο Εσόδων: %.2f €\n", totalRevenues);
        System.out.printf(" Σύνολο Εξόδων: %.2f €\n", totalExpenses);

        double balance = totalRevenues - totalExpenses;
        if (balance >= 0) {
            System.out.printf(" Πλεόνασμα: %.2f €\n", balance);
        } else {
            System.out.printf(" Έλλειμμα: %.2f €\n", Math.abs(balance));
        }
    }
}
