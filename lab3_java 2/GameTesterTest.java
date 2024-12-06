import java.util.Scanner;

abstract class GameTester {
    protected String name;
    protected boolean isFullTime;

    public GameTester(String name, boolean isFullTime) {
        this.name = name;
        this.isFullTime = isFullTime;
    }

    public abstract double determineSalary();
}

class FullTimeGameTester extends GameTester {

    public FullTimeGameTester(String name) {
        super(name, true);
    }

    @Override
    public double determineSalary() {
        return 3000; // Fixed salary for full-time game testers
    }
}

class PartTimeGameTester extends GameTester {
    private int hoursWorked;

    public PartTimeGameTester(String name, int hoursWorked) {
        super(name, false);
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double determineSalary() {
        return hoursWorked * 20; // $20 per hour for part-time game testers
    }
}

public class GameTesterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name of the game tester: ");
        String name = scanner.nextLine();

        System.out.print("Is the tester full-time (true/false)? ");
        boolean isFullTime = scanner.nextBoolean();

        GameTester tester;

        if (isFullTime) {
            tester = new FullTimeGameTester(name);
        } else {
            System.out.print("Enter hours worked: ");
            int hours = scanner.nextInt();
            tester = new PartTimeGameTester(name, hours);
        }

        System.out.println("The salary for " + name + " is $" + tester.determineSalary());

        scanner.close();
    }
}