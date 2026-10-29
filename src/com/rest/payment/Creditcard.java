package com.rest.payment;

public class Creditcard implements PaymentMethod {
    @Override
    public PaymentResult pay(double amount) {
        if (amount <= 0 || Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Amount must be a valid positive number");
        }
        return new PaymentResult(
                "Credit card", amount, 0, "Simulated credit card payment approved");
    }
}
