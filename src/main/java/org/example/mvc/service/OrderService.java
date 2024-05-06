package org.example.mvc.service;

import org.example.mvc.dto.CartDto;
import org.example.mvc.dto.OrderDto;
import org.example.mvc.model.Order;
import org.example.mvc.model.User;

import java.util.HashMap;
import java.util.List;

public interface OrderService {
    void saveService(HashMap<Long, CartDto> cart, User user, OrderDto orderDto);

    List<Order> findOrderByUser(User user);
}
