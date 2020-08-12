package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;

import java.util.List;

public interface OrderService {
    Order findByOrdnum(long id);

    List<Order> getOrdersByAdvance();

    Order save(Order order);
}
