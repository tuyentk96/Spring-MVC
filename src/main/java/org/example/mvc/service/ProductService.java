package org.example.mvc.service;

import org.example.mvc.dto.CreateProductRequest;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<String> addProduct(CreateProductRequest createProductRequest) throws IOException;
}
