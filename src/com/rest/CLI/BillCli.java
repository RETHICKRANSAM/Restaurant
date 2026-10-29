package com.rest.CLI;

import com.rest.models.Bill;
import com.rest.services.BillService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BillCli {
    private final BillService billService;
    private final Scanner scanner;

    public BillCli(BillService billService, Scanner scanner) {
        if (billService == null || scanner == null) {
            throw new IllegalArgumentException("Bill service and scanner are required");
        }
        this.billService = billService;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== BILL RECORDS ===");
            System.out.println("1. View all bills");
            System.out.println("2. Find bill by ID");
            System.out.println("0. Back");

            Integer choice = readInt("Enter your choice: ");
            if (choice == null) {
                return;
            }
            switch (choice) {
                case 1:
                    viewAllBills();
                    break;
                case 2:
                    findBillById();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Select 0, 1, or 2.");
            }
        }
    }

    private void viewAllBills() {
        List<Bill> bills = billService.getAllBills();
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
            return;
        }
        for (Bill bill : bills) {
            printBill(bill);
        }
    }

    private void findBillById() {
        Integer id = readInt("Enter bill ID: ");
        if (id == null) {
            return;
        }
        Optional<Bill> bill = billService.getBillById(id);
        if (!bill.isPresent()) {
            System.out.println("Bill not found.");
            return;
        }
        printBill(bill.get());
    }

    private void printBill(Bill bill) {
        System.out.println(bill);
        for (String item : bill.getItems()) {
            System.out.println("  " + item);
        }
    }

    private Integer readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return null;
            }
            try {
                return Integer.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}
