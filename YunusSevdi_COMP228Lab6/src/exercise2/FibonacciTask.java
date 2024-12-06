package exercise2;

import javafx.concurrent.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FibonacciTask extends Task<List<Integer>> {
    private final int count;

    public FibonacciTask(int count) {
        this.count = count;
    }

    @Override
    protected List<Integer> call() throws Exception {
        List<Integer> fibonacciNumbers = new ArrayList<>();
        int a = 0, b = 1;

        for (int i = 0; i < count; i++) {
            if (isCancelled()) break;

            // Add the current number to the list
            fibonacciNumbers.add(a);
            System.out.println("Fibonacci number added: " + a); // Debugging log

            // Calculate the next number
            int next = a + b;
            a = b;
            b = next;

            // Simulate delay for demonstration
            Thread.sleep(2000); 
        }

        // Reverse the list to display in reverse order
        Collections.reverse(fibonacciNumbers);
        return fibonacciNumbers;
    }
}
