package com.rest.services;

import com.rest.models.Order;
import com.rest.models.Payment;
import com.rest.payment.PaymentMethod;
import com.rest.payment.PaymentResult;
import com.rest.repository.PaymentRepository;
import java.util.List;

public class PaymentService {
    private final PaymentRepository repository;
    private final OrderService orderService;
    private int nextPaymentId = 1;

    public PaymentService(OrderService orderService) {
        if (orderService == null) {
            throw new IllegalArgumentException("Order service cannot be null");
        }
        this.orderService = orderService;
        this.repository = new PaymentRepository();
    }

    public Payment processPayment(PaymentMethod method, double amount) {
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        PaymentResult result = method.pay(amount);
        return savePayment(result);
    }

    public PaymentResult payAndComplete(int orderId, PaymentMethod method) {
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        Order order = orderService.getOrder(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getStatus() != Order.OrderStatus.OPEN) {
            throw new IllegalStateException("Order is not open");
        }
        if (order.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot pay for an order with no items");
        }

        PaymentResult result = method.pay(order.getTotal());
        savePayment(result);
        orderService.completeOrder(orderId);
        return result;
    }

    private Payment savePayment(PaymentResult result) {
        Payment payment = new Payment(
                nextPaymentId++, result.getAmount(), result.getMethod(), "SUCCESS");
        repository.save(payment);
        return payment;
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Payment getPaymentById(int id) {
        return repository.findById(id);
    }
}
