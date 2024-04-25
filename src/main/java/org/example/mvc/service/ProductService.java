package org.example.mvc.service;

import org.example.mvc.dto.ProductDto;
import org.example.mvc.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void addProduct(ProductDto productDto);

    List<Product> getAllProduct();

    void deleteProduct(Long id);

    Page<Product> getAllProductPage(String keyword, PageRequest pageRequest, Long categoryId);

    void fakeProductWithExcel(MultipartFile file) throws IOException;
}
