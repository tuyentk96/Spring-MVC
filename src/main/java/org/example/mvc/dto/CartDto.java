package org.example.mvc.dto;

import lombok.Data;
import org.example.mvc.model.Product;
@Data
public class CartDto {
    private Product product;
    private int quantity;
    private Float totalPrice;
}
