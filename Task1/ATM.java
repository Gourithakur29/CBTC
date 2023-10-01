import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String userId;
    private String userPin;
    private double balance;
    private List<String> transactionHistory;

    public User(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean validateUser(String userId, String userPin) {
        return this.userId.equals(userId) && this.userPin.equals(userPin);
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposit: +" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawal: -" + amount);
            return true;
        }
        return false;
    }

    public void transfer(User recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory.add("Transfer: -" + amount + " to " + recipient.userId);
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create two example user accounts
        User user1 = new User("gourithakur@1", "1234", 2000.0);
        User user2 = new User("gourithakur@2", "5678", 1000.0);

        User currentUser = null;

        System.out.println("Welcome to the ATM");

        // Prompt for user ID and PIN
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String userPin = scanner.nextLine();

        if (user1.validateUser(userId, userPin)) {
            currentUser = user1;
        } else if (user2.validateUser(userId, userPin)) {
            currentUser = user2;
        } else {
            System.out.println("Invalid User ID or PIN. Exiting.");
            System.exit(0);
        }

        System.out.println("Welcome, " + userId + "!");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Your balance is: $" + currentUser.getBalance());
                    break;
                case 2:
                    System.out.print("Enter the amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    System.out.println("Deposited $" + depositAmount + " successfully.");
                    break;
                case 3:
                    System.out.print("Enter the amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    if (currentUser.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawn $" + withdrawAmount + " successfully.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 4:
                    System.out.print("Enter the recipient's User ID: ");
                    String recipientUserId = scanner.next();
                    System.out.print("Enter the amount to transfer: $");
                    double transferAmount = scanner.nextDouble();

                    if (user1.validateUser(recipientUserId, "") || user2.validateUser(recipientUserId, "")) {
                        User recipient = user1.validateUser(recipientUserId, "") ? user1 : user2;
                        currentUser.transfer(recipient, transferAmount);
                        System.out.println("Transferred $" + transferAmount + " to " + recipientUserId + " successfully.");
                    } else {
                        System.out.println("Recipient User ID not found.");
                    }
                    break;
                case 5:
                    currentUser.printTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
