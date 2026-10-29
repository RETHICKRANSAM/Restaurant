package com.rest.payment;

public interface PaymentMethod {
    PaymentResult pay(double amount);
}
