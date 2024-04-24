package org.example.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.example.mvc.model.Category;
import org.example.mvc.service.CategoryService;
import org.example.mvc.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public String home(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        return "home";
    }
}
