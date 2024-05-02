package org.example.mvc.dao;

import org.example.mvc.dto.CartDto;

import java.util.HashMap;

public interface CartDAO {
    HashMap<Long, CartDto> addCart(Long id, HashMap<Long,CartDto> cart, int qty);
    HashMap<Long,CartDto> editCart(Long id,int quantity,HashMap<Long,CartDto> cart);
    HashMap<Long,CartDto> deleteCart(Long id,HashMap<Long,CartDto> cart);
    int totalQuantity(HashMap<Long,CartDto> cart);
    float totalPrice(HashMap<Long,CartDto> cart);
}
