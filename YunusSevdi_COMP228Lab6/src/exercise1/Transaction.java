package exercise1;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Transaction implements Runnable {
    private final Account account;
    private final boolean isDeposit;
    private final double amount;

    // Constructor
    public Transaction(Account account, boolean isDeposit, double amount) {
        this.account = account;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    // Run method to perform transaction
    @Override
    public void run() {
        if (isDeposit) {
            account.deposit(amount);
        } else {
            account.withdraw(amount);
        }
        System.out.println(Thread.currentThread().getName() + " Balance after transaction: " + account.getBalance());
    }
}
