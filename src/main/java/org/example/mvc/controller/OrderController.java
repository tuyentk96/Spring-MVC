package org.example.mvc.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mvc.dto.CartDto;
import org.example.mvc.dto.OrderDto;
import org.example.mvc.model.Order;
import org.example.mvc.model.User;
import org.example.mvc.service.CartService;
import org.example.mvc.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    @PostMapping("/order-success")
    public String order(@Valid @ModelAttribute("orderDto") OrderDto orderDto, BindingResult result, Model model, HttpSession session) {
        HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("Cart");
        User user = (User) session.getAttribute("user");
        if (result.hasErrors()) {
            float totalPrice = cartService.totalPrice(cart);
            model.addAttribute("totalPrice",totalPrice);
            return "cart";
        }
        if (cart == null) {
            return "redirect:/index";
        }
        if (user == null) {
            return "redirect:/login";
        }
        orderService.saveService(cart,user,orderDto);
        session.removeAttribute("Cart");
        return "order-success";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Order> orders = orderService.findOrderByUser(user);
        model.addAttribute("orders", orders);
        return "order";
    }
}
