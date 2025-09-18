import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        OUTER:
        while (true) {
            System.out.println("\n--- Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Total Expenses");
            System.out.println("4. Export & Predict Spending");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter expense name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter expense amount: ");
                    double amount = sc.nextDouble();
                    manager.addExpense(name, amount);
                    System.out.println("Expense added!");
                }
                case 2 -> manager.viewExpenses();
                case 3 -> manager.totalExpenses();
                case 4 -> {
                   manager.exportToCSV("../python/data/expenses.csv");
                    try {
                        Process p = Runtime.getRuntime().exec("python3 ../python/predictor.py");
                        p.waitFor();
                        BufferedReader reader = new BufferedReader(new FileReader("../data/prediction.txt"));
                        String prediction = reader.readLine();
                        reader.close();
                        System.out.println("Predicted Spending for Next Week: " + prediction);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Error running Python script: " + e.getMessage());
                    }
                }
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    break OUTER;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
        sc.close();
    }
}
