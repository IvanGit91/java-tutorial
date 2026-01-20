package tutorial.innerclass;

/*
* Use Case: Banking System with Account Notifications
Imagine a banking application where each BankAccount has a feature to notify the user of important account updates.
* This can be implemented using inner classes to encapsulate the notification logic, keeping it closely tied to the account.*/

public class BankAccount {

    private final String accountHolderName;
    private double balance;

    public BankAccount(String accountHolderName, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public static void main(String[] args) {
        // Create a BankAccount
        BankAccount account = new BankAccount("Alice", 500);

        // Deposit and Withdraw with notifications
        account.deposit(200);  // Deposit Notification
        account.withdraw(700); // Low Balance Alert
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;

        // Notify the user about the deposit
        Notification notification = new Notification();
        notification.sendDepositNotification(amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient funds for withdrawal.");
            return;
        }

        balance -= amount;

        // Check if low balance notification is needed
        Notification notification = new Notification();
        notification.sendLowBalanceAlert();
    }

    // Regular Inner Class for notifications
    public class Notification {
        public void sendLowBalanceAlert() {
            if (balance < 100) {
                System.out.println("Dear " + accountHolderName + ", your account balance is low: $" + balance);
            }
        }

        public void sendDepositNotification(double amount) {
            System.out.println("Dear " + accountHolderName + ", a deposit of $" + amount + " was made to your account. New balance: $" + balance);
        }
    }
}
