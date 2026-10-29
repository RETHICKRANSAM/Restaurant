package com.rest.CLI;

import com.rest.models.MenuItem;
import com.rest.models.Bill;
import com.rest.models.Order;
import com.rest.models.OrderItem;
import com.rest.models.Table;
import com.rest.payment.PaymentResult;
import com.rest.payment.Cash;
import com.rest.payment.Creditcard;
import com.rest.payment.PaymentMethod;
import com.rest.payment.Upi;
import com.rest.services.BillService;
import com.rest.services.MenuService;
import com.rest.services.OrderService;
import com.rest.services.PaymentService;
import com.rest.services.TableService;
import java.util.List;
import java.util.Scanner;

public class TableCLI {
    private final TableService tableService;
    private final MenuService menuService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final PaymentCli paymentCli;
    private final BillService billService;
    private final BillCli billCli;
    private final Scanner scanner;

    public TableCLI(TableService tableService, MenuService menuService,
            OrderService orderService, PaymentService paymentService,
            BillService billService, Scanner scanner) {
        this.tableService = tableService;
        this.menuService = menuService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.billService = billService;
        this.scanner = scanner;
        this.paymentCli = new PaymentCli(paymentService, scanner);
        this.billCli = new BillCli(billService, scanner);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== RESTAURANT MANAGEMENT ===");
            System.out.println("1. View all tables");
            System.out.println("2. View menu");
            System.out.println("3. Create order");
            System.out.println("4. View orders");
            System.out.println("5. Pay for and complete order");
            System.out.println("6. Payment records");
            System.out.println("7. Bill records");
            System.out.println("0. Exit");

            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    showTables();
                    break;
                case 2:
                    showMenu();
                    break;
                case 3:
                    createOrder();
                    break;
                case 4:
                    showOrders();
                    break;
                case 5:
                    payForOrder();
                    break;
                case 6:
                    paymentCli.start();
                    break;
                case 7:
                    billCli.start();
                    break;
                case 0:
                    System.out.println("Thank you");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void showTables() {
        System.out.println("\n=== TABLES ===");
        for (Table table : tableService.getAllTables()) {
            System.out.println(table);
        }
    }

    private void showMenu() {
        System.out.println("\n=== MENU ===");
        for (MenuItem item : menuService.getAllMenuItems()) {
            System.out.println(item);
        }
    }

    private void createOrder() {
        showTables();
        int tableId = readInt("Enter the free table ID: ");
        Order order;
        try {
            order = orderService.createOrder(tableId);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("Could not create order: " + exception.getMessage());
            return;
        }

        System.out.println("Created " + order);
        showMenu();

        boolean addingItems = true;
        while (addingItems) {
            int menuItemId = readInt("Enter menu item ID (0 to finish): ");
            if (menuItemId == 0) {
                addingItems = false;
                continue;
            }
            int quantity = readInt("Enter quantity: ");
            try {
                OrderItem item = orderService.addItem(order.getId(), menuItemId, quantity);
                System.out.println("Added " + item);
            } catch (IllegalArgumentException | IllegalStateException exception) {
                System.out.println("Could not add item: " + exception.getMessage());
            }
        }
        System.out.println("Order total: " + order.getTotal());
    }

    private void showOrders() {
        System.out.println("\n=== ORDERS ===");
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
            return;
        }
        for (Order order : orders) {
            System.out.println(order);
            for (OrderItem item : order.getItems()) {
                System.out.println("  " + item);
            }
        }
    }

    private void payForOrder() {
        showOrders();
        int orderId = readInt("Enter order ID to complete: ");
        try {
            Order order = orderService.getOrder(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));
            System.out.println("Amount due: " + order.getTotal());
            System.out.println("1. Cash");
            System.out.println("2. Credit card (simulated)");
            System.out.println("3. UPI (simulated)");

            int paymentChoice = readInt("Choose payment method: ");
            PaymentMethod paymentMethod;
            switch (paymentChoice) {
                case 1:
                    paymentMethod = new Cash(readDouble("Cash received: "));
                    break;
                case 2:
                    paymentMethod = new Creditcard();
                    break;
                case 3:
                    paymentMethod = new Upi();
                    break;
                default:
                    System.out.println("Invalid payment method.");
                    return;
            }

            PaymentResult result = paymentService.payAndComplete(orderId, paymentMethod);
            Bill bill = billService.generateBill(order, result);
            System.out.println(result.getMessage());
            System.out.println("Paid by: " + result.getMethod());
            System.out.println("Amount paid: " + result.getAmount());
            if (result.getChange() > 0) {
                System.out.println("Change: " + result.getChange());
            }
            System.out.println("Order completed; its table is now free.");
            System.out.println("Bill generated: " + bill);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("Could not process payment: " + exception.getMessage());
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}
