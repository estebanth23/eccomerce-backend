package com.xeo.ecommerce.backend.infrastructure.service;

import com.xeo.ecommerce.backend.application.UserService;
import com.xeo.ecommerce.backend.domain.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService{
    private UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).
                password(user.getPassword()).roles(user.getUserType().name()).build();
    }
}
