package com.rest.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    public enum OrderStatus {
        OPEN, COMPLETED, CANCELLED
    }

    private final int id;
    private final Table table;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status;

    public Order(int id, Table table) {
        if (id <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        if (table == null) {
            throw new IllegalArgumentException("Table cannot be null");
        }

        this.id = id;
        this.table = table;
        this.status = OrderStatus.OPEN;
    }

    public int getId() {
        return id;
    }

    public Table getTable() {
        return table;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addItem(MenuItem menuItem, int quantity) {
        if (status != OrderStatus.OPEN) {
            throw new IllegalStateException("Items can only be added to an open order");
        }
        items.add(new OrderItem(menuItem, quantity));
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void setStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Order status cannot be null");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order ID: " + id + ", Table ID: " + table.getId()
                + ", Status: " + status + ", Total: " + getTotal();
    }
}
