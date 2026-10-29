package com.rest.CLI;

import com.rest.models.Payment;
import com.rest.payment.Cash;
import com.rest.payment.Creditcard;
import com.rest.payment.PaymentMethod;
import com.rest.payment.Upi;
import com.rest.services.PaymentService;
import java.util.List;
import java.util.Scanner;

public class PaymentCli {
    private final PaymentService paymentService;
    private final Scanner scanner;

    public PaymentCli(PaymentService paymentService, Scanner scanner) {
        if (paymentService == null || scanner == null) {
            throw new IllegalArgumentException("Payment service and scanner are required");
        }
        this.paymentService = paymentService;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== PAYMENT RECORDS ===");
            System.out.println("1. Make Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. Find Payment by ID");
            System.out.println("0. Exit");
            Integer choice = readInt("Enter your choice: ");
            if (choice == null) {
                return;
            }

            switch (choice) {
                case 1:
                    makePayment();
                    break;
                case 2:
                    viewAllPayments();
                    break;
                case 3:
                    findPaymentById();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Select 0, 1, 2, or 3.");
            }
        }
    }

    private void makePayment() {
        Double amount = readPositiveAmount("Enter payment amount: ");
        if (amount == null) {
            return;
        }

        System.out.println("1. Cash");
        System.out.println("2. Credit Card (simulated)");
        System.out.println("3. UPI (simulated)");
        Integer choice = readInt("Choose payment method: ");
        if (choice == null) {
            return;
        }

        PaymentMethod method;
        switch (choice) {
            case 1:
                Double cashReceived = readNonNegativeAmount("Cash received: ");
                if (cashReceived == null) {
                    return;
                }
                method = new Cash(cashReceived);
                break;
            case 2:
                method = new Creditcard();
                break;
            case 3:
                method = new Upi();
                break;
            default:
                System.out.println("Invalid payment method.");
                return;
        }

        try {
            Payment payment = paymentService.processPayment(method, amount);
            System.out.println("Payment saved: " + payment);
        } catch (IllegalArgumentException exception) {
            System.out.println("Could not process payment: " + exception.getMessage());
        }
    }

    private void viewAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        if (payments.isEmpty()) {
            System.out.println("No payments found.");
            return;
        }
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }

    private void findPaymentById() {
        Integer id = readInt("Enter payment ID: ");
        if (id == null) {
            return;
        }
        Payment payment = paymentService.getPaymentById(id);
        if (payment == null) {
            System.out.println("Payment not found.");
            return;
        }
        System.out.println(payment);
    }

    private Integer readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String input = scanner.nextLine().trim();
            try {
                return Integer.valueOf(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private Double readPositiveAmount(String prompt) {
        while (true) {
            Double amount = readAmount(prompt);
            if (amount == null) {
                return null;
            }
            if (amount > 0 && Double.isFinite(amount)) {
                return amount;
            }
            System.out.println("Amount must be a positive, finite number.");
        }
    }

    private Double readNonNegativeAmount(String prompt) {
        while (true) {
            Double amount = readAmount(prompt);
            if (amount == null) {
                return null;
            }
            if (amount >= 0 && Double.isFinite(amount)) {
                return amount;
            }
            System.out.println("Amount must be a non-negative, finite number.");
        }
    }

    private Double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String input = scanner.nextLine().trim();
            try {
                return Double.valueOf(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
