package org.example.mvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mvc.dao.OrderDAO;
import org.example.mvc.dao.OrderDetailDAO;
import org.example.mvc.dto.CartDto;
import org.example.mvc.dto.OrderDto;
import org.example.mvc.model.Order;
import org.example.mvc.model.OrderDetail;
import org.example.mvc.model.User;
import org.example.mvc.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;
    @Override
    public void saveService(HashMap<Long, CartDto> cart, User user, OrderDto orderDto) {
        Order order = new Order();
        order.setUser(user);
        order.setNote(orderDto.getNote());
        order.setAddress(orderDto.getAddress());

        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

        for (Map.Entry<Long,CartDto> cartItem : cart.entrySet()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartItem.getValue().getProduct());
            orderDetail.setQuantity(cartItem.getValue().getQuantity());
            orderDetailDAO.save(orderDetail);
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        orderDAO.save(order);
    }

    @Override
    public List<Order> findOrderByUser(User user) {
        return orderDAO.findAllByUser(user);
    }
}
