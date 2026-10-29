package com.rest;

import com.rest.CLI.TableCLI;
import com.rest.services.BillService;
import com.rest.services.TableService;
import com.rest.services.MenuService;
import com.rest.services.OrderService;
import com.rest.services.PaymentService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TableService tableService = new TableService();
        MenuService menuService = new MenuService();
        OrderService orderService = new OrderService(menuService, tableService);
        PaymentService paymentService = new PaymentService(orderService);
        BillService billService = new BillService();
        TableCLI cli = new TableCLI(
                tableService, menuService, orderService, paymentService, billService,
                new Scanner(System.in));
        cli.start();
    }
}