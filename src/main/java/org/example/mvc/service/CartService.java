package org.example.mvc.service;

import org.example.mvc.dto.CartDto;

import java.util.HashMap;

public interface CartService {
    HashMap<Long, CartDto> addCart(Long id, HashMap<Long, CartDto> cart, int qty);

    int totalQuantity(HashMap<Long, CartDto> cart);

    float totalPrice(HashMap<Long, CartDto> cart);

    HashMap<Long, CartDto> deleteCart(Long id, HashMap<Long, CartDto> cart);
}
