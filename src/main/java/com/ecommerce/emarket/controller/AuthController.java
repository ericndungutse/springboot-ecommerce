package com.ecommerce.emarket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.emarket.security.jwt.JwtUtils;
import com.ecommerce.emarket.security.request.LoginRequestDTO;
import com.ecommerce.emarket.security.response.LoginResponseDTO;
import com.ecommerce.emarket.security.services.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/api/signin")
    public ResponseEntity<?> sign(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication;
        try {
            // If this passes, the user is authenticated
            // It Calls the loadUserByUsername method in the UserDetailsService
            // It Then calls passwordEncoder.matches() to compare the password
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()));

        } catch (AuthenticationException exception) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid username or password");
            response.put("status", false);
            return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
        }

        // SecurityContextHolder.getContext().setAuthentication(authentication);

        // UserDetails userDetails =
        // userDetailsService.loadUserByUsername(loginRequestDTO.getUsername());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(userDetails.getId(), jwt, userDetails.getUsername(),
                roles);

        return new ResponseEntity<Object>(loginResponseDTO, HttpStatus.OK);
    }
}
