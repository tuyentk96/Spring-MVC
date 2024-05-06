package org.example.mvc.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.mvc.dto.CartDto;
import org.example.mvc.dto.OrderDto;
import org.example.mvc.model.Product;
import org.example.mvc.service.CartService;
import org.example.mvc.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @RequestMapping("/addCart")
    public String addCart(HttpServletRequest request, @RequestParam("id") Long id,
                          @RequestParam("qty") int qty,
                          HttpSession session) {
        HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("Cart");
        if (cart==null){
            cart = new HashMap<Long,CartDto>();
        }
        cart = cartService.addCart(id,cart,qty);

        session.setAttribute("Cart",cart);

        return "redirect:"+request.getHeader("Referer");
    }

    @RequestMapping("/deleteCart")
    public String deleteCart(HttpServletRequest request, @RequestParam("id") Long id, HttpSession session){
        HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("Cart");
        if (cart==null){
            cart = new HashMap<Long,CartDto>();
        }
        cart = cartService.deleteCart(id,cart);
        session.setAttribute("Cart",cart);
        return "redirect:"+request.getHeader("Referer");
    }

    @RequestMapping("/cart")
    public String cart(HttpSession session,Model model){
        OrderDto orderDto = new OrderDto();
        HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("Cart");
        if (cart==null){
            cart = new HashMap<Long,CartDto>();
        }
        float totalPrice = cartService.totalPrice(cart);
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("orderDto",orderDto);
        return "cart";
    }
}
