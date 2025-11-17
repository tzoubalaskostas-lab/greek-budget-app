package gr.budget;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Revenue {
    @JsonProperty("incomesource")
    private String incomesource;

    @JsonProperty("ammount")
    private double amount;

    private int year;

    public String getIncomesource() { return incomesource; }
    public void setIncomesource(String incomesource) { this.incomesource = incomesource; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return incomesource + " → " + String.format("%.2f €", amount) + " (" + year + ")";
    }
    
}
