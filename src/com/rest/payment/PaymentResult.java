package com.rest.payment;

public class PaymentResult {
    private final String method;
    private final double amount;
    private final double change;
    private final String message;

    public PaymentResult(String method, double amount, double change, String message) {
        this.method = method;
        this.amount = amount;
        this.change = change;
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public double getAmount() {
        return amount;
    }

    public double getChange() {
        return change;
    }

    public String getMessage() {
        return message;
    }
}
