package gr.budget;

public class Expense {
  private String ministry;
    private double amount_eur;
    private int year;

    public String getMinistry() { return ministry; }
    public void setMinistry(String ministry) { this.ministry = ministry; }

    public double getAmount_eur() { return amount_eur; }
    public void setAmount_eur(double amount_eur) { this.amount_eur = amount_eur; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    
    @Override
    public String toString(){
        return ministry + " → " + String.format("%.2f €", amount_eur) + " (" + year + ")";
    }
}
