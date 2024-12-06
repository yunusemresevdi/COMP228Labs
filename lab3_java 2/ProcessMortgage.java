import java.util.Scanner;

interface MortgageConstants {
    int SHORT_TERM = 1;
    int MEDIUM_TERM = 3;
    int LONG_TERM = 5;
    double MAX_AMOUNT = 300000;
    String BANK_NAME = "CityToronto Bank";
}

abstract class Mortgage {
    protected String mortgageNumber;
    protected String customerName;
    protected double amount;
    protected double interestRate;
    protected int term;

    public Mortgage(String mortgageNumber, String customerName, double amount, double interestRate, int term) {
        if (amount > MortgageConstants.MAX_AMOUNT) {
            System.out.println("Mortgage amount exceeds maximum limit. Setting amount to maximum allowed.");
            this.amount = MortgageConstants.MAX_AMOUNT;
        } else {
            this.amount = amount;
        }

        this.mortgageNumber = mortgageNumber;
        this.customerName = customerName;
        this.interestRate = interestRate;
        
        if (term != MortgageConstants.SHORT_TERM && term != MortgageConstants.MEDIUM_TERM && term != MortgageConstants.LONG_TERM) {
            this.term = MortgageConstants.SHORT_TERM; // Default to short-term
        } else {
            this.term = term;
        }
    }

    public abstract String getMortgageInfo();
}

class BusinessMortgage extends Mortgage {
    public BusinessMortgage(String mortgageNumber, String customerName, double amount, double primeRate, int term) {
        super(mortgageNumber, customerName, amount, primeRate + 0.01, term); // 1% over prime rate
    }

    @Override
    public String getMortgageInfo() {
        return "Business Mortgage\n" +
                "Mortgage Number: " + mortgageNumber +
                "\nCustomer Name: " + customerName +
                "\nAmount: $" + amount +
                "\nInterest Rate: " + interestRate +
                "\nTerm: " + term + " years\n";
    }
}

class PersonalMortgage extends Mortgage {
    public PersonalMortgage(String mortgageNumber, String customerName, double amount, double primeRate, int term) {
        super(mortgageNumber, customerName, amount, primeRate + 0.02, term); // 2% over prime rate
    }

    @Override
    public String getMortgageInfo() {
        return "Personal Mortgage\n" +
                "Mortgage Number: " + mortgageNumber +
                "\nCustomer Name: " + customerName +
                "\nAmount: $" + amount +
                "\nInterest Rate: " + interestRate +
                "\nTerm: " + term + " years\n";
    }
}

public class ProcessMortgage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Mortgage[] mortgages = new Mortgage[3];

        System.out.print("Enter the current prime interest rate: ");
        double primeRate = scanner.nextDouble();

        for (int i = 0; i < mortgages.length; i++) {
            System.out.print("Enter mortgage type (1 for Business, 2 for Personal): ");
            int type = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter mortgage number: ");
            String mortgageNumber = scanner.nextLine();

            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();

            System.out.print("Enter mortgage amount: ");
            double amount = scanner.nextDouble();

            System.out.print("Enter mortgage term (1, 3, 5 years): ");
            int term = scanner.nextInt();

            if (type == 1) {
                mortgages[i] = new BusinessMortgage(mortgageNumber, customerName, amount, primeRate, term);
            } else if (type == 2) {
                mortgages[i] = new PersonalMortgage(mortgageNumber, customerName, amount, primeRate, term);
            }
        }

        // Display all mortgages
        for (Mortgage mortgage : mortgages) {
            System.out.println(mortgage.getMortgageInfo());
        }

        scanner.close();
    }
}