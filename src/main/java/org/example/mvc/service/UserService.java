package org.example.mvc.service;

import org.example.mvc.dto.UserDto;
import org.example.mvc.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService{
    void saveUser(UserDto userDto);

    User findByUsername(String username);
}
