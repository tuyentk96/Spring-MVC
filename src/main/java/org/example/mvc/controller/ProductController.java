package org.example.mvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mvc.dto.ProductDto;
import org.example.mvc.model.Category;
import org.example.mvc.model.Product;
import org.example.mvc.service.CategoryService;
import org.example.mvc.service.ProductService;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public String productManager(Model model) {
        List<Product> products = productService.getAllProduct();
        model.addAttribute("products", products);
        return "product-manager";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add-product")
    public String addProduct(Model model){
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "add-product";
    }
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/product";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add-product/process",method = RequestMethod.POST)
    public String addProductProcess(@Valid @ModelAttribute ProductDto productDto, BindingResult result,Model model) {
        String contentType = productDto.getImage().getContentType();

        if (productDto.getImage().isEmpty()){
            result.addError(new FieldError("productDto", "image", "Vui lòng nhập hình ảnh cho sản phẩm"));
        }else if (contentType == null || !contentType.startsWith("image/")){
            result.addError(new FieldError("productDto", "image", "Định dạng file không đúng"));
        }

        if (productDto.getImage().getSize()> 10*1024*1024){
            result.addError(new FieldError("productDto", "image", "Kích thước hình ảnh quá lớn"));
        }

        if (result.hasErrors()){
            List<Category> categories = categoryService.getAllCategory();
            model.addAttribute("categories", categories);
            return "/add-product";
        }

       productService.addProduct(productDto);

        return "redirect:/product";
    }

    @GetMapping("/detail")
    public String detailProduct(@RequestParam("id") Long id,Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/fake")
    public String fake(){
        return "fake-product/fake";
    }

    @PostMapping(value = "/fake-product",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fakeProductWithExcel(@ModelAttribute MultipartFile file) throws IOException {
        productService.fakeProductWithExcel(file);
        return "redirect:/product";
    }
}
