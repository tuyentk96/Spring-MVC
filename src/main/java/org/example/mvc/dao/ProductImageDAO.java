package org.example.mvc.dao;

import org.example.mvc.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageDAO extends JpaRepository<ProductImage, Long> {
}
