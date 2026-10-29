package com.rest.models;

public class MenuItem {
    private final int id;
    private final String name;
    private final double price;
    private boolean available;

    public MenuItem(int id, String name, double price) {
        if (id <= 0) {
            throw new IllegalArgumentException("Menu item ID must be positive");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Menu item name cannot be empty");
        }
        if (price < 0 || Double.isNaN(price) || Double.isInfinite(price)) {
            throw new IllegalArgumentException("Price must be a valid non-negative number");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Menu item ID: " + id + ", Name: " + name
                + ", Price: " + price + ", Available: " + available;
    }
}
