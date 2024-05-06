package org.example.mvc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderDto {
    private String note;
    @NotBlank(message = "Bạn cần nhập địa chỉ")
    private String address;
}
