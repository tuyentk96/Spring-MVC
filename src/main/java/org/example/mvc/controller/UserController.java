package org.example.mvc.controller;


import lombok.RequiredArgsConstructor;
import org.example.mvc.dto.UserDto;
import org.example.mvc.service.UserService;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto) {
        userService.saveUser(userDto);
        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(){
        return "redirect:/index";
    }
}
