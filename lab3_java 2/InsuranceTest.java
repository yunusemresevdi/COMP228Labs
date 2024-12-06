import java.util.Scanner;

abstract class Insurance {
    protected String insuranceType;
    protected double monthlyCost;

    public String getInsuranceType() {
        return insuranceType;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public abstract void setInsuranceCost(double cost);
    public abstract void displayInfo();
}

class Life extends Insurance {

    public Life() {
        this.insuranceType = "Life Insurance";
    }

    @Override
    public void setInsuranceCost(double cost) {
        this.monthlyCost = cost;
    }

    @Override
    public void displayInfo() {
        System.out.println("Insurance Type: " + insuranceType);
        System.out.println("Monthly Cost: $" + monthlyCost);
    }
}

class Health extends Insurance {

    public Health() {
        this.insuranceType = "Health Insurance";
    }

    @Override
    public void setInsuranceCost(double cost) {
        this.monthlyCost = cost;
    }

    @Override
    public void displayInfo() {
        System.out.println("Insurance Type: " + insuranceType);
        System.out.println("Monthly Cost: $" + monthlyCost);
    }
}

public class InsuranceTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Insurance[] insurances = new Insurance[2]; // Assuming two insurances for testing

        // Get Life Insurance details
        System.out.print("Enter the monthly cost for Life Insurance: ");
        double lifeCost = scanner.nextDouble();
        Insurance life = new Life();
        life.setInsuranceCost(lifeCost);
        insurances[0] = life;

        // Get Health Insurance details
        System.out.print("Enter the monthly cost for Health Insurance: ");
        double healthCost = scanner.nextDouble();
        Insurance health = new Health();
        health.setInsuranceCost(healthCost);
        insurances[1] = health;

        // Display information for each insurance
        for (Insurance insurance : insurances) {
            insurance.displayInfo();
        }

        scanner.close();
    }
}