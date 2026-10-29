package com.rest.services;

import com.rest.models.Bill;
import com.rest.models.Order;
import com.rest.models.OrderItem;
import com.rest.payment.PaymentResult;
import com.rest.repository.BillRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillService {
    private final BillRepository repository = new BillRepository();
    private int nextBillId = 1;

    public Bill generateBill(Order order, PaymentResult paymentResult) {
        if (order == null || paymentResult == null) {
            throw new IllegalArgumentException("Order and successful payment are required");
        }
        if (order.getStatus() != Order.OrderStatus.COMPLETED) {
            throw new IllegalStateException("A bill can only be generated for a completed order");
        }
        if (repository.findByOrderId(order.getId()).isPresent()) {
            throw new IllegalStateException("A bill already exists for this order");
        }
        if (Double.compare(paymentResult.getAmount(), order.getTotal()) != 0) {
            throw new IllegalArgumentException("Payment amount does not match the order total");
        }

        List<String> itemDescriptions = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            itemDescriptions.add(item.toString());
        }

        Bill bill = new Bill(nextBillId++, order.getId(), order.getTable().getId(),
                itemDescriptions, order.getTotal(), paymentResult.getMethod());
        repository.save(bill);
        return bill;
    }

    public List<Bill> getAllBills() {
        return repository.findAll();
    }

    public Optional<Bill> getBillById(int id) {
        return repository.findById(id);
    }
}
