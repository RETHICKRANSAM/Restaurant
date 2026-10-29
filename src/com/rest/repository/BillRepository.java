package com.rest.repository;

import com.rest.models.Bill;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BillRepository {
    private final List<Bill> bills = new ArrayList<>();

    public void save(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("Bill cannot be null");
        }
        bills.add(bill);
    }

    public List<Bill> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(bills));
    }

    public Optional<Bill> findById(int id) {
        for (Bill bill : bills) {
            if (bill.getId() == id) {
                return Optional.of(bill);
            }
        }
        return Optional.empty();
    }

    public Optional<Bill> findByOrderId(int orderId) {
        for (Bill bill : bills) {
            if (bill.getOrderId() == orderId) {
                return Optional.of(bill);
            }
        }
        return Optional.empty();
    }
}
