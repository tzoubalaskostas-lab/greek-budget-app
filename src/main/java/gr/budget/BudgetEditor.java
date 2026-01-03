package gr.budget;

import java.util.List;
import java.util.Scanner;

public class BudgetEditor {


      public static void openEditor(List<Expense> WorkingExpenses, List<Revenue> WorkingRevenues, Scanner scanner, List<Expense> OriginalExpenses, List<Revenue> OriginalRevenues) {

        System.setProperty("file.encoding", "UTF-8");


        boolean running = true;

        while (running) {
            System.out.println("\n========= ΕΡΓΑΛΕΙΟ ΕΠΕΞΕΡΓΑΣΙΑΣ ΠΡΟΥΠΟΛΟΓΙΣΜΟΥ =========");
            System.out.println("1. Επεξεργασία εξόδων");
            System.out.println("2. Επεξεργασία εσόδων");
            System.out.println("3. Εμφάνιση λιστών και ισοζυγίου");
            System.out.println("4. Εμφάνιση στατιστικών με βάση τις αλλαγές σου");
            System.out.println("5. επαναφορά στοιχείων (reset sandbox)");
            System.out.println("0. Επιστροφή στο κεντρικό μενού");
            System.out.print("Επιλογή: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    editExpenses(WorkingExpenses, scanner);
                    break;
                case 2:
                    editRevenues(WorkingRevenues, scanner);
                    break;
                case 3:
                    showExpenses(WorkingExpenses);
                    showRevenues(WorkingRevenues);
                    displayBalance(WorkingExpenses, WorkingRevenues);
                    break;
                case 4:
                    System.out.println(" Εμφανίζονται Στατιστικά που αφορούν τον προϋπολογισμό με τις αλλαγές σου");
                    double texp = WorkingExpenses.stream().mapToDouble(Expense::getAmount_eur).sum();
                    double trev = WorkingRevenues.stream().mapToDouble(Revenue::getAmount).sum();
                    Statistics.showBudgetPercentages( WorkingExpenses, WorkingRevenues, trev, texp);
                    break;
                case 5:
                    resetLists(WorkingExpenses, WorkingRevenues, OriginalExpenses, OriginalRevenues);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Μη έγκυρη επιλογή.");
            }
        }
    }

    public static void showExpenses(List<Expense> WorkingExpenses) {
        System.out.println("\n--- ΕΞΟΔΑ ---");
        for (int i = 0; i < WorkingExpenses.size(); i++) {
            Expense e = WorkingExpenses.get(i);
            System.out.printf("%d. %s → %.2f € (%d)\n", i + 1, e.getMinistry(), e.getAmount_eur(), e.getYear());
        }
    }

    public static void showRevenues(List<Revenue> WorkingRevenues) {
        System.out.println("\n--- ΕΣΟΔΑ ---");
        for (int i = 0; i < WorkingRevenues.size(); i++) {
            Revenue r = WorkingRevenues.get(i);
            System.out.printf("%d. %s → %.2f € (%d)\n", i + 1, r.getIncomesource(), r.getAmount(), r.getYear());
        }
    }

    public static void editExpenses(List<Expense> WorkingExpenses, Scanner scanner) {
        showExpenses(WorkingExpenses);
        System.out.print("Επιλέξτε αριθμό εξόδου για αλλαγή: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        Expense e = WorkingExpenses.get(index);

        System.out.print(" ποσό που θα προστεθεί στο υπουργείο (€) : ");
        double amountStr = scanner.nextDouble();
        if (!(amountStr + e.getAmount_eur() < 0 )) {
            e.setAmount_eur(amountStr + e.getAmount_eur());
        } else {
            System.out.println("Δεν επιτρέπεται το υπόλοιπο των εξόδων του Υπουργείου να ειναι αρνητικό");
            
        }

        System.out.println("Αλλαγή στα έξοδα καταχωρήθηκε.");
    }

    public static void editRevenues(List<Revenue> WorkingRevenues, Scanner scanner) {
        showRevenues(WorkingRevenues);
        System.out.print("Επιλέξτε αριθμό εσόδου για αλλαγή: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        Revenue r = WorkingRevenues.get(index);

        System.out.print(" ποσό που θα προστεθεί στην πηγή εσόδων (€) : ");
        double amountStr = scanner.nextDouble();
        if (!(amountStr + r.getAmount() < 0 )) {
            r.setAmount(amountStr + r.getAmount());
        } else {
            System.out.println(" Δεν επιτρέπεται η πηγή εσόδων να είναι αρνητική ");
            
        }

        System.out.println(" Αλλαγή στα έσοδα καταχωρήθηκε.");
    }



    public static void displayBalance(List<Expense> WorkingExpenses, List<Revenue> WorkingRevenues) {
        double totalExpenses = 0;
        double totalRevenues = 0;

        for (Expense e : WorkingExpenses) totalExpenses += e.getAmount_eur();
        for (Revenue r : WorkingRevenues) totalRevenues += r.getAmount();

        System.out.println("\n--- ΙΣΟΖΥΓΙΟ ---");
        System.out.printf("Σύνολο εξόδων: %.2f €\n", totalExpenses);
        System.out.printf("Σύνολο εσόδων: %.2f €\n", totalRevenues);
        System.out.printf("Ισοζύγιο: %.2f €\n", (totalRevenues - totalExpenses));
    }

     public static void resetLists(
        List<Expense> workingExpenses,
        List<Revenue> workingRevenues,
        List<Expense> originalExpenses,
        List<Revenue> originalRevenues) {

        // Reset expenses
        workingExpenses.clear();
        for (Expense e : originalExpenses) {
            workingExpenses.add(new Expense(
                    e.getMinistry(),
                    e.getAmount_eur(),
                    e.getYear()
            ));
        }

        // Reset revenues
        workingRevenues.clear();
        for (Revenue r : originalRevenues) {
            workingRevenues.add(new Revenue(
                    r.getIncomesource(),
                    r.getAmount(),
                    r.getYear()
            ));
        }

        System.out.println("✔ Οι αλλαγές επανήλθαν στις αρχικές τιμές.");

    
    }


}
