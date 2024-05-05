package org.example.mvc.controller;


import lombok.RequiredArgsConstructor;
import org.example.mvc.dto.UserDto;
import org.example.mvc.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private  AuthenticationManager authenticationManager;


    @GetMapping("/register")
    public String regsiter(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping("/register/confirm")
    public String register(@ModelAttribute UserDto userDto) {
        userService.saveUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
