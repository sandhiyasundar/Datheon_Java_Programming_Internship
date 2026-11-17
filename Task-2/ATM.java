
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

// Custom Exception for Insufficient Funds
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Custom Exception for Invalid PIN
class InvalidPINException extends Exception {
    public InvalidPINException(String message) {
        super(message);
    }
}

// Abstract Account Class
abstract class Account {

    protected String accountNumber;
    protected int pin;
    protected double balance;

    protected ArrayList<String> transactionLog =
            new ArrayList<String>();

    Account(String accountNumber, int pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    // PIN Verification
    public void verifyPIN(int enteredPIN)
            throws InvalidPINException {

        if (enteredPIN != pin) {
            throw new InvalidPINException("Invalid PIN!");
        }
    }

    // Deposit Method
    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Enter a valid amount!");
            return;
        }

        balance = balance + amount;

        logTransaction("Deposit");

        System.out.println("Amount deposited successfully!");
    }

    // Abstract Withdrawal Method
    public abstract void withdraw(double amount)
            throws InsufficientFundsException;

    // Abstract Interest Method
    public abstract double calculateInterest();

    // Transaction Logging
    protected void logTransaction(String type) {

        String log = LocalDateTime.now()
                + " | " + type
                + " | Balance: Rs." + balance;

        transactionLog.add(log);
    }

    // Balance Enquiry
    public void showBalance() {
        System.out.println("Current Balance: Rs." + balance);
    }

    // Transaction History
    public void showTransactions() {

        System.out.println("\n--- Transaction History ---");

        if (transactionLog.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        for (String transaction : transactionLog) {
            System.out.println(transaction);
        }
    }
}

// Savings Account Class
class SavingsAccount extends Account {

    private double interestRate;

    SavingsAccount(String accNo, int pin,
                   double balance, double rate) {

        super(accNo, pin, balance);
        this.interestRate = rate;
    }

    @Override
    public void withdraw(double amount)
            throws InsufficientFundsException {

        if (amount <= 0) {
            System.out.println("Enter a valid amount!");
            return;
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Insufficient funds in Savings Account!");
        }

        balance = balance - amount;

        logTransaction("Withdrawal");

        System.out.println("Withdrawal successful!");
    }

    @Override
    public double calculateInterest() {

        return balance * interestRate / 100;
    }

    public void addInterest() {

        double interest = calculateInterest();

        balance = balance + interest;

        logTransaction("Interest Added");

        System.out.println("Interest added: Rs." + interest);
    }
}

// Checking Account Class
class CheckingAccount extends Account {

    private double overdraftLimit;

    CheckingAccount(String accNo, int pin,
                    double balance, double limit) {

        super(accNo, pin, balance);
        this.overdraftLimit = limit;
    }

    @Override
    public void withdraw(double amount)
            throws InsufficientFundsException {

        if (amount <= 0) {
            System.out.println("Enter a valid amount!");
            return;
        }

        if (amount > balance + overdraftLimit) {
            throw new InsufficientFundsException(
                    "Overdraft limit exceeded!");
        }

        balance = balance - amount;

        logTransaction("Withdrawal");

        System.out.println("Withdrawal successful!");
    }

    @Override
    public double calculateInterest() {
        return 0;
    }
}

// Customer Class
class Customer {

    String name;
    Account account;

    Customer(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    void displayCustomer() {

        System.out.println("\nWelcome, " + name);

        System.out.println(
                "Account Number: " + account.accountNumber);
    }
}

// Main ATM Class
public class ATM {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== BANK ATM SYSTEM =====");

        System.out.println("1. Savings Account");
        System.out.println("2. Checking Account");

        System.out.print("Choose Account Type: ");

        int choice = sc.nextInt();

        Account account;

        if (choice == 1) {

            account = new SavingsAccount(
                    "SA1001", 1234, 5000, 4.0);

        } else if (choice == 2) {

            account = new CheckingAccount(
                    "CA1001", 1234, 5000, 2000);

        } else {

            System.out.println("Invalid Account Type!");

            sc.close();
            return;
        }

        // Create Customer
        Customer customer =
                new Customer("Sandhiya", account);

        customer.displayCustomer();

        // PIN Verification
        try {

            System.out.print("\nEnter PIN: ");

            int enteredPIN = sc.nextInt();

            account.verifyPIN(enteredPIN);

            System.out.println("Login Successful!");

        } catch (InvalidPINException e) {

            System.out.println(e.getMessage());

            sc.close();
            return;
        }

        int option = 0;

        // ATM Menu
        do {

            System.out.println("\n===== ATM MENU =====");

            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Calculate Interest");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            option = sc.nextInt();

            try {

                switch (option) {

                    case 1:

                        account.showBalance();
                        break;

                    case 2:

                        System.out.print(
                                "Enter deposit amount: ");

                        double deposit = sc.nextDouble();

                        account.deposit(deposit);
                        break;

                    case 3:

                        System.out.print(
                                "Enter withdrawal amount: ");

                        double withdraw = sc.nextDouble();

                        account.withdraw(withdraw);
                        break;

                    case 4:

                        if (account instanceof SavingsAccount) {

                            SavingsAccount savings =
                                    (SavingsAccount) account;

                            savings.addInterest();

                        } else {

                            System.out.println(
                                    "Interest is not applicable.");
                        }

                        break;

                    case 5:

                        account.showTransactions();
                        break;

                    case 6:

                        System.out.println(
                                "Thank you for using ATM!");

                        break;

                    default:

                        System.out.println("Invalid Choice!");
                }

            } catch (InsufficientFundsException e) {

                System.out.println(e.getMessage());
            }

        } while (option != 6);

        sc.close();
    }
}