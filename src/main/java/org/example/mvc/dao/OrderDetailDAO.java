package org.example.mvc.dao;

import org.example.mvc.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {
}
