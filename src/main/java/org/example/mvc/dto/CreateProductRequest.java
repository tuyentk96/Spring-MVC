package org.example.mvc.dto;

import lombok.Data;
import org.example.mvc.model.Category;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProductRequest {

    private String name;

    private Float price;

    private MultipartFile image;

    private Float sale;

    private String color;

    private String brand;

    private String origin;

    private String description;

    private Category category;
}
