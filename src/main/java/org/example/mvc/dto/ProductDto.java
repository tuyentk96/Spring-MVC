package org.example.mvc.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    @NotEmpty(message = "Vui lòng nhập tên sản phẩm")
    private String name;

    @Min(value = 0,message = "Giá phải trên 0đ")
    private Float price;

    private MultipartFile image;

    private Float sale;

    private String color;

    @NotEmpty(message = "Vui lòng nhập thương hiệu sản phẩm")
    private String brand;

    @NotEmpty(message = "Vui lòng nhập sản xuất sản phẩm")
    private String origin;

    private String description;

    private Long categoryId;
}
