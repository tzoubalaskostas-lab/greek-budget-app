package gr.budget;

import java.util.List;
import java.util.Scanner;

public class BudgetEditor {


      public static void openEditor(List<Expense> expenses, List<Revenue> revenues, Scanner scanner) {

        System.setProperty("file.encoding", "UTF-8");

        boolean running = true;

        while (running) {
            System.out.println("\n========= ΕΡΓΑΛΕΙΟ ΕΠΕΞΕΡΓΑΣΙΑΣ ΠΡΟΥΠΟΛΟΓΙΣΜΟΥ =========");
            System.out.println("1. Επεξεργασία εξόδων");
            System.out.println("2. Επεξεργασία εσόδων");
            System.out.println("3. Εμφάνιση λιστών και ισοζυγίου");
            System.out.println("0. Επιστροφή στο κεντρικό μενού");
            System.out.print("Επιλογή: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    editExpenses(expenses, scanner);
                    break;
                case 2:
                    editRevenues(revenues, scanner);
                    break;
                case 3:
                    showExpenses(expenses);
                    showRevenues(revenues);
                    displayBalance(expenses, revenues);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Μη έγκυρη επιλογή.");
            }
        }
    }

    public static void showExpenses(List<Expense> expenses) {
        System.out.println("\n--- ΕΞΟΔΑ ---");
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            System.out.printf("%d. %s → %.2f € (%d)\n", i + 1, e.getMinistry(), e.getAmount_eur(), e.getYear());
        }
    }

    public static void showRevenues(List<Revenue> revenues) {
        System.out.println("\n--- ΕΣΟΔΑ ---");
        for (int i = 0; i < revenues.size(); i++) {
            Revenue r = revenues.get(i);
            System.out.printf("%d. %s → %.2f € (%d)\n", i + 1, r.getIncomesource(), r.getAmount(), r.getYear());
        }
    }

    public static void editExpenses(List<Expense> expenses, Scanner scanner) {
        showExpenses(expenses);
        System.out.print("Επιλέξτε αριθμό εξόδου για αλλαγή: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        Expense e = expenses.get(index);

        System.out.print(" ποσό που θα προστεθεί στο υπουργείο (€) : ");
        double amountStr = scanner.nextDouble();
        if (!(amountStr + e.getAmount_eur() < 0 )) {
            e.setAmount_eur(amountStr + e.getAmount_eur());
        } else {
            System.out.println("Δεν επιτρέπεται το υπόλοιπο των εξόδων του Υπουργείου να ειναι αρνητικό");
            
        }

        System.out.println("Αλλαγή στα έξοδα καταχωρήθηκε.");
    }

    public static void editRevenues(List<Revenue> revenues, Scanner scanner) {
        showRevenues(revenues);
        System.out.print("Επιλέξτε αριθμό εσόδου για αλλαγή: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        Revenue r = revenues.get(index);

        System.out.print(" ποσό που θα προστεθεί στην πηγή εσόδων (€) : ");
        double amountStr = scanner.nextDouble();
        if (!(amountStr + r.getAmount() < 0 )) {
            r.setAmount(amountStr + r.getAmount());
        } else {
            System.out.println(" Δεν επιτρέπεται η πηγή εσόδων να είναι αρνητική ");
            
        }

        System.out.println(" Αλλαγή στα έσοδα καταχωρήθηκε.");
    }



    public static void displayBalance(List<Expense> expenses, List<Revenue> revenues) {
        double totalExpenses = 0;
        double totalRevenues = 0;

        for (Expense e : expenses) totalExpenses += e.getAmount_eur();
        for (Revenue r : revenues) totalRevenues += r.getAmount();

        System.out.println("\n--- ΙΣΟΖΥΓΙΟ ---");
        System.out.printf("Σύνολο εξόδων: %.2f €\n", totalExpenses);
        System.out.printf("Σύνολο εσόδων: %.2f €\n", totalRevenues);
        System.out.printf("Ισοζύγιο: %.2f €\n", (totalRevenues - totalExpenses));
    }
    
}
