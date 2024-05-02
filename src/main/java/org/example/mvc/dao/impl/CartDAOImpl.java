package org.example.mvc.dao.impl;

import lombok.RequiredArgsConstructor;
import org.example.mvc.dao.CartDAO;
import org.example.mvc.dao.ProductDAO;
import org.example.mvc.dto.CartDto;
import org.example.mvc.model.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CartDAOImpl implements CartDAO {

    private final ProductDAO productDAO;
    @Override
    public HashMap<Long, CartDto> addCart(Long id, HashMap<Long, CartDto> cart, int qty) {
        CartDto cartItem = new CartDto();
        Product products = productDAO.findById(id).orElseThrow();
        if (products!=null && cart.containsKey(id)){
            cartItem = cart.get(id);
            cartItem.setQuantity(cartItem.getQuantity()+qty);
            cartItem.setTotalPrice(cartItem.getQuantity()*products.getPrice());
        }else {
            cartItem.setProduct(products);
            cartItem.setQuantity(qty);
            cartItem.setTotalPrice(products.getPrice());
        }
        cart.put(id,cartItem);
        return cart;
    }

    @Override
    public HashMap<Long, CartDto> editCart(Long id, int quantity, HashMap<Long, CartDto> cart) {
        return null;
    }

    @Override
    public HashMap<Long, CartDto> deleteCart(Long id, HashMap<Long, CartDto> cart) {
        if (cart == null){
            return null;
        }
        if (cart.containsKey(id)){
            cart.remove(id);
        }
        return cart;
    }

    @Override
    public int totalQuantity(HashMap<Long, CartDto> cart) {
        int totalQuantity = 0;
        for (Map.Entry<Long,CartDto> cartItem : cart.entrySet()){
            totalQuantity += cartItem.getValue().getQuantity();
        }
        return totalQuantity;
    }

    @Override
    public float totalPrice(HashMap<Long, CartDto> cart) {
        float totalPrice = 0;

        for (Map.Entry<Long,CartDto> cartItem : cart.entrySet()){
            totalPrice += (cartItem.getValue().getProduct().getPrice()
                    - cartItem.getValue().getProduct().getPrice()*cartItem.getValue().getProduct().getSale()/100)
                    *cartItem.getValue().getQuantity();
        }

        return totalPrice;
    }
}
