package org.example.mvc.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String retypePassword;
}
