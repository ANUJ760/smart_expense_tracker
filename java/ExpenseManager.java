import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExpenseManager {
    private ArrayList<Expense> expenses;

    public ExpenseManager() {
        expenses = new ArrayList<>();
    }

    public void addExpense(String name, double amount) {
        expenses.add(new Expense(name, amount));
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            for (int i = 0; i < expenses.size(); i++) {
                System.out.println((i + 1) + ". " + expenses.get(i));
            }
        }
    }

    public void totalExpenses() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        System.out.println("Total Expenses: ₹" + total);
    }

    public void exportToCSV(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("name,amount\n");
            for (Expense e : expenses) {
                writer.write(e.getName() + "," + e.getAmount() + "\n");
            }
            writer.close();
            System.out.println("Data exported to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
