package com.rest.repository;

import com.rest.models.Order;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    public void save(Order order) {
        orders.add(order);
    }

    public List<Order> findAll() {
        return Collections.unmodifiableList(orders);
    }

    public Optional<Order> findById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }
}
