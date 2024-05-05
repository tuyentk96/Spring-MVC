package org.example.mvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mvc.dao.RoleDAO;
import org.example.mvc.dao.UserDAO;

import org.example.mvc.dto.UserDto;
import org.example.mvc.model.User;
import org.example.mvc.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;



    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleDAO.findById(1L).orElseThrow());
        userDAO.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorityList);
    }
}
