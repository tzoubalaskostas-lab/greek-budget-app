package gr.budget;
import java.util.List;

public class Statistics {

    

    
    public static void showBudgetPercentages(List<Expense> expenses, List<Revenue> revenues, double trev, double texp) {
        System.out.println("__________________________________");
        System.out.println("Εμφάνιση ποσοστών ανά υπουργείο");
        System.out.println("__________________________________");

        for(int i = 0 ; i < expenses.size(); i++){
            Expense e = expenses.get(i);
            double percent = e.getAmount_eur()/texp * 100;
            System.out.printf( "%-30s %.1f%%\n",e.getMinistry(), percent);
        }

        System.out.println("__________________________________");
        System.out.println("Εμφάνιση ποσοστών ανά πηγή εσόδων");
        System.out.println("__________________________________");
        
        for (int i = 0 ; i < revenues.size(); i++){
            Revenue r = revenues.get(i);
            double percent = r.getAmount()/trev * 100;
            System.out.printf("%-30s %.1f%%\n", r.getIncomesource(), percent);

        }

        System.out.println("======================================");
        System.out.println("Τελικός ποσοστιαίος προυπολογισμός");
        System.out.println("======================================");
        
        double budgetpercent = trev/texp * 100;
        System.out.printf("%.1f%%\n", budgetpercent);
    }

}
