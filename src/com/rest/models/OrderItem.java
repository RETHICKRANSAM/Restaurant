package com.rest.models;

public class OrderItem {
    private final MenuItem menuItem;
    private final int quantity;
    private final double unitPrice;

    public OrderItem(MenuItem menuItem, int quantity) {
        if (menuItem == null) {
            throw new IllegalArgumentException("Menu item cannot be null");
        }
        if (!menuItem.isAvailable()) {
            throw new IllegalArgumentException("Menu item is not available");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        this.menuItem = menuItem;
        this.quantity = quantity;
        this.unitPrice = menuItem.getPrice();
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return menuItem.getName() + " x " + quantity + " @ "
                + unitPrice + " = " + getSubtotal();
    }
}
