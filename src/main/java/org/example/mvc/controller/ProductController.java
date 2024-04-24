package org.example.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.example.mvc.dto.CreateProductRequest;
import org.example.mvc.model.Category;
import org.example.mvc.model.Product;
import org.example.mvc.service.CategoryService;
import org.example.mvc.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final ProductService productService;
    @GetMapping("")
    public String productManager() {
        return "product-manager";
    }

    @GetMapping("/add-product")
    public String addProduct(Model model){
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        CreateProductRequest product = new CreateProductRequest();
        model.addAttribute("product", product);
        return "add-product";
    }

    @RequestMapping(value = "/add-product/process",method = RequestMethod.POST)
    public String addProductProcess(@ModelAttribute CreateProductRequest createProductRequest, Model model) throws IOException {

        List<String> errors = productService.addProduct(createProductRequest);

        return "product-manager";
    }
}
