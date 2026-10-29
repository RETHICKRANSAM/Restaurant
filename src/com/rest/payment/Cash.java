package com.rest.payment;

public class Cash implements PaymentMethod {
    private final double amountReceived;

    public Cash(double amountReceived) {
        if (amountReceived < 0 || Double.isNaN(amountReceived)
                || Double.isInfinite(amountReceived)) {
            throw new IllegalArgumentException("Cash received must be a valid non-negative amount");
        }
        this.amountReceived = amountReceived;
    }

    @Override
    public PaymentResult pay(double amount) {
        if (amount <= 0 || Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Amount due must be a valid positive number");
        }
        if (amountReceived < amount) {
            throw new IllegalArgumentException("Cash received is less than the amount due");
        }
        return new PaymentResult(
                "Cash", amount, amountReceived - amount, "Cash payment accepted");
    }
}
