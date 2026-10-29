package com.rest.services;

import com.rest.models.MenuItem;
import com.rest.models.Order;
import com.rest.models.OrderItem;
import com.rest.models.Table;
import com.rest.repository.OrderRepository;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuService menuService;
    private final TableService tableService;
    private int nextOrderId = 1;

    public OrderService(MenuService menuService, TableService tableService) {
        this.orderRepository = new OrderRepository();
        this.menuService = menuService;
        this.tableService = tableService;
    }

    public Order createOrder(int tableId) {
        Table table = tableService.getTableById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table not found"));
        if (table.getStatus() != Table.TableStatus.FREE) {
            throw new IllegalStateException("Table is already occupied");
        }

        Order order = new Order(nextOrderId++, table);
        table.setStatus(Table.TableStatus.OCCUPIED);
        orderRepository.save(order);
        return order;
    }

    public OrderItem addItem(int orderId, int menuItemId, int quantity) {
        Order order = getOrder(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        MenuItem menuItem = menuService.getMenuItemById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
        order.addItem(menuItem, quantity);
        return order.getItems().get(order.getItems().size() - 1);
    }

    public void completeOrder(int orderId) {
        Order order = getOrder(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getStatus() != Order.OrderStatus.OPEN) {
            throw new IllegalStateException("Order is not open");
        }

        order.setStatus(Order.OrderStatus.COMPLETED);
        order.getTable().setStatus(Table.TableStatus.FREE);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrder(int id) {
        return orderRepository.findById(id);
    }
}
