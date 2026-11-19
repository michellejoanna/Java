import java.io.*;
import java.util.*;

class InvalidAmountException extends Exception {
    InvalidAmountException(String message) { super(message); }
}
class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) { super(message); }
}
class ATM {
    private double balance = 5000.0;
    double withdraw(String account, String amountText, boolean cardFileExists)
            throws InvalidAmountException, InsufficientFundsException, IOException {
        if (!cardFileExists) throw new FileNotFoundException("Card reader file not found");
        int accountNo = Integer.parseInt(account);
        double amount = Double.parseDouble(amountText);
        if (accountNo <= 0) throw new IllegalArgumentException("Account number must be positive");
        if (amount <= 0) throw new InvalidAmountException("Withdrawal must be positive");
        if (amount > balance) throw new InsufficientFundsException("Insufficient account balance");
        balance -= amount;
        return balance;
    }
}
public class ATMWithdrawal {
    static void test(String label, String account, String amount, boolean file) {
        ATM atm = new ATM();
        System.out.println("\nTEST: " + label);
        try {
            double remaining = atm.withdraw(account, amount, file);
            System.out.printf(Locale.US, "Cash dispensed. Remaining balance: %.2f%n", remaining);
        } catch (NumberFormatException | IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("Transaction rejected: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Device error: " + e.getMessage());
        } finally {
            System.out.println("ATM session cleanup completed (finally).");
        }
    }
    public static void main(String[] args) {
        test("Valid", "12345", "500", true);
        test("Letters in amount", "12345", "abc", true);
        test("Negative amount", "12345", "-50", true);
        test("More than balance", "12345", "9000", true);
        test("Missing card-reader file", "12345", "100", false);
        test("Invalid account", "-2", "100", true);
    }
}