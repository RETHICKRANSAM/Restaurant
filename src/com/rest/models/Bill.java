package com.rest.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bill {
    private final int id;
    private final int orderId;
    private final int tableId;
    private final List<String> items;
    private final double total;
    private final String paymentMethod;

    public Bill(int id, int orderId, int tableId, List<String> items,
            double total, String paymentMethod) {
        if (id <= 0 || orderId <= 0 || tableId <= 0) {
            throw new IllegalArgumentException("Bill, order, and table IDs must be positive");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Bill must contain at least one item");
        }
        if (total <= 0 || Double.isNaN(total) || Double.isInfinite(total)) {
            throw new IllegalArgumentException("Bill total must be a valid positive amount");
        }
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be empty");
        }

        this.id = id;
        this.orderId = orderId;
        this.tableId = tableId;
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
        this.total = total;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTableId() {
        return tableId;
    }

    public List<String> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String toString() {
        return "Bill ID: " + id + ", Order ID: " + orderId + ", Table ID: " + tableId
                + ", Total: " + String.format("%.2f", total)
                + ", Paid by: " + paymentMethod;
    }
}
