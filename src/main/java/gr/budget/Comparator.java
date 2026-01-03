package gr.budget;

import java.util.List;

public class Comparator {

    public static void compareBudgets(
            List<Expense> originalExpenses,
            List<Expense> workingExpenses,
            List<Revenue> originalRevenues,
            List<Revenue> workingRevenues) {

        System.out.println("\n===== ΣΥΓΚΡΙΣΗ ΠΡΟΥΠΟΛΟΓΙΣΜΩΝ =====");

        compareExpenses(originalExpenses, workingExpenses);
        compareRevenues(originalRevenues, workingRevenues);
    }

    private static void compareExpenses(
            List<Expense> original,
            List<Expense> working) {

        System.out.println("\n--- Σύγκριση Εξόδων ανά Υπουργείο ---");

        for (int i = 0; i < original.size(); i++) {
            Expense o = original.get(i);
            Expense w = working.get(i);

            double diff = w.getAmount_eur() - o.getAmount_eur();
            double percent = (diff / o.getAmount_eur()) * 100;

            System.out.printf(
                "%-30s | %+,.2f € | %+,.1f%%\n",
                o.getMinistry(),
                diff,
                percent
            );
        }
    }

    private static void compareRevenues(
            List<Revenue> original,
            List<Revenue> working) {

        System.out.println("\n--- Σύγκριση Εσόδων ανά Πηγή ---");

        for (int i = 0; i < original.size(); i++) {
            Revenue o = original.get(i);
            Revenue w = working.get(i);

            double diff = w.getAmount() - o.getAmount();
            double percent = (diff / o.getAmount()) * 100;

            System.out.printf(
                "%-30s | %+,.2f € | %+,.1f%%\n",
                o.getIncomesource(),
                diff,
                percent
            );
        }
    }
}
