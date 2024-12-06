package exercise1;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountTest {
    public static void main(String[] args) {
        // Create an Account with an initial balance
        Account account = new Account(1000.0);

        // Create a list of transactions
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(account, true, 200));   // Deposit 200
        transactions.add(new Transaction(account, false, 150));  // Withdraw 150
        transactions.add(new Transaction(account, true, 300));   // Deposit 300
        transactions.add(new Transaction(account, false, 50));   // Withdraw 50

        // Use ExecutorService to execute transactions
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (Transaction transaction : transactions) {
            executor.execute(transaction);
        }

        // Shutdown the executor
        executor.shutdown();

        // Wait for all tasks to complete
        while (!executor.isTerminated()) {
            // Wait for termination
        }

        // Display final balance
        System.out.println("Final Balance: " + account.getBalance());
    }
}
