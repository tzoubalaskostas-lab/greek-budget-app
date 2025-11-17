package gr.budget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;


public class App {
     public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        InputStream expStream = App.class.getResourceAsStream("/expenses.json");
        List<Expense> expenses = mapper.readValue(expStream, new TypeReference<List<Expense>>() {});

        InputStream revStream = App.class.getResourceAsStream("/revenues.json");
        List<Revenue> revenues = mapper.readValue(revStream, new TypeReference<List<Revenue>>() {});

        System.out.println("📊 Greek Budget 2025 Simulation\n");

        System.out.println("🧾 EXPENSES (per Ministry):");
        expenses.forEach(System.out::println);

        System.out.println("\n💰 REVENUES (per Source):");
        revenues.forEach(System.out::println);

        double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount_eur).sum();
        double totalRevenues = revenues.stream().mapToDouble(Revenue::getAmount).sum();

        System.out.printf("\nTotal Revenues: %.2f €\n", totalRevenues);
        System.out.printf("Total Expenses: %.2f €\n", totalExpenses);

        double balance = totalRevenues - totalExpenses;
        
        if (balance >= 0) {
            System.out.printf("✅ Surplus: %.2f €\n", balance);
        } else {
            System.out.printf("⚠️ Deficit: %.2f €\n", Math.abs(balance));
        }
        
      
    }
}
