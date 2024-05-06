package org.example.mvc.dao;

import org.example.mvc.model.Order;
import org.example.mvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {
    Order findByUser(User user);

    List<Order> findAllByUser(User user);
}
