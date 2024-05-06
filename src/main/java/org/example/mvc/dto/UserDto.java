package org.example.mvc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank(message = "Bạn cần nhập thông tin username")
    private String username;
    @NotBlank(message = "Bạn cần nhập password")
    private String password;
    @NotBlank(message = "Bạn cần nhập lại password")
    private String retypePassword;
}
