package org.example.mvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mvc.dao.CartDAO;
import org.example.mvc.dto.CartDto;
import org.example.mvc.service.CartService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartDAO cartDAO;
    @Override
    public HashMap<Long, CartDto> addCart(Long id, HashMap<Long, CartDto> cart, int qty) {
        return cartDAO.addCart(id,cart,qty);
    }

    @Override
    public int totalQuantity(HashMap<Long, CartDto> cart) {
        return cartDAO.totalQuantity(cart);
    }

    @Override
    public float totalPrice(HashMap<Long, CartDto> cart) {
        return cartDAO.totalPrice(cart);
    }

    @Override
    public HashMap<Long, CartDto> deleteCart(Long id, HashMap<Long, CartDto> cart) {
        return cartDAO.deleteCart(id,cart);
    }
}
