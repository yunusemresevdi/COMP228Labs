package exercise1;
public class Account {
    private double balance;

    // Constructor to initialize balance
    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized method for deposit
    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " deposited: " + amount);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to deposit invalid amount: " + amount);
        }
    }

    // Synchronized method for withdraw
    public synchronized void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew: " + amount);
        } else if (amount > balance) {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw more than balance: " + amount);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw invalid amount: " + amount);
        }
    }

    // Getter for balance
    public synchronized double getBalance() {
        return balance;
    }
}
