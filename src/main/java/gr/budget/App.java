package gr.budget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {

     public static void main(String[] args) throws Exception {
 
       System.setProperty("file.encoding", "UTF-8");

//δημιουργουμε αντικειμενο mapper για να διαβασει τα αρχεια json και να τα μετατρεψει σε λιστες προσπελασιμες απο java
       
        ObjectMapper mapper = new ObjectMapper();

        InputStream expStream = App.class.getResourceAsStream("/expenses.json");
        List<Expense> OriginalExpenses = mapper.readValue(expStream, new TypeReference<List<Expense>>() {});

        InputStream revStream = App.class.getResourceAsStream("/revenues.json");
        List<Revenue> OriginalRevenues = mapper.readValue(revStream, new TypeReference<List<Revenue>>() {});

       List<Expense> WorkingExpenses = new ArrayList<>();
        for (Expense e : OriginalExpenses) {
            WorkingExpenses.add(new Expense(e.getMinistry(), e.getAmount_eur(), e.getYear()));
        }

        List<Revenue> WorkingRevenues = new ArrayList<>();
for (Revenue r : OriginalRevenues) {
    WorkingRevenues.add(new Revenue(r.getIncomesource(), r.getAmount(), r.getYear()));
}

        System.out.println("📊 Greek Budget 2025 Simulation\n");

// εμφανιζουμε τις δυο λιστες με τα εσοδα και εξοδα του κρατους οπως αυτα δημιουργηθηκαν  παραπανω απο τα αρχεια json

        System.out.println("🧾 EXPENSES (per Ministry):");
        OriginalExpenses.forEach(System.out::println);

        System.out.println("\n💰 REVENUES (per Source):");
        OriginalRevenues.forEach(System.out::println);

        double totalExpenses = OriginalExpenses.stream().mapToDouble(Expense::getAmount_eur).sum();
        double totalRevenues = OriginalRevenues.stream().mapToDouble(Revenue::getAmount).sum();

        System.out.printf("\nTotal Revenues: %.2f €\n", totalRevenues);
        System.out.printf("Total Expenses: %.2f €\n", totalExpenses);

        getbalance(totalRevenues, totalExpenses);


         Scanner scanner = new Scanner(System.in);

     while (true) {
            System.out.println("\n==================================");
            System.out.println("🏛️  ΕΛΛΗΝΙΚΟΣ ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ 2025");
            System.out.println("==================================");
            System.out.println("1. 🔧 Μεταβολή στοιχείων προϋπολογισμού (User sandbox)");
            System.out.println("2. 📊 Εμφάνιση κατάστασης πραγματικού προϋπολογισμού");
            System.out.println("0. 🔚 Έξοδος");
            System.out.print("\nΕπιλέξτε μια επιλογή: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // καθάρισμα buffer

            if (choice == 1) {
                System.out.println("➡ Επιλέξατε: User Sandbox (test cases) ");
                BudgetEditor.openEditor(WorkingExpenses, WorkingRevenues, scanner, OriginalExpenses, OriginalRevenues );
            }
                





            else if (choice == 2) {
                System.out.println("➡ Επιλέξατε: Εμφάνιση προϋπολογισμού ");
                
                 System.out.printf("\nTotal Revenues: %.2f €\n", totalRevenues);
                 System.out.printf("Total Expenses: %.2f €\n", totalExpenses);
                getbalance(totalRevenues, totalExpenses);
            }





            else if (choice == 0) {
                System.out.println("🔚 Έξοδος από την εφαρμογή. Αντίο!");
                break;
            }




            else {
                System.out.println("❗ Μη έγκυρη επιλογή. Προσπαθήστε ξανά.");
            }
        }

     scanner.close();
   
    }

      
    

      
    public static void getbalance( double trev, double texp ){

        double balance = trev - texp;

        if (balance >= 0) {
            System.out.printf("✅ ο προυπολογισμός είναι πλεονασματικός με Πλεόνασμα προυπολογισμού: %.2f €\n", balance);
        } else {
            System.out.printf("⚠️ο προυπολογισμός είναι ελλειμματικός με Έλλειμμα προυπολογισμού: %.2f €\n", Math.abs(balance));
        }
    }
}
