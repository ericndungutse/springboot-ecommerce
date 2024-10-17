package com.ecommerce.emarket.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ecommerce.emarket.model.User;
import com.ecommerce.emarket.repositories.UserRepository;

@Component
public class AuthUtil {
    @Autowired
    UserRepository userRepository;

    public String loggedInEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication
                .getName())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + authentication.getName()));
        return user.getEmail();
    }

    public User loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication
                .getName())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + authentication.getName()));
    }

}
