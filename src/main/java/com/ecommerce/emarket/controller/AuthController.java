package com.ecommerce.emarket.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.emarket.exceptions.APIException;
import com.ecommerce.emarket.model.AppRole;
import com.ecommerce.emarket.model.Role;
import com.ecommerce.emarket.model.User;
import com.ecommerce.emarket.payload.MessageResponse;
import com.ecommerce.emarket.repositories.RoleRepository;
import com.ecommerce.emarket.repositories.UserRepository;
import com.ecommerce.emarket.security.jwt.JwtUtils;
import com.ecommerce.emarket.security.request.LoginRequestDTO;
import com.ecommerce.emarket.security.request.SignupRequest;
import com.ecommerce.emarket.security.response.LoginResponseDTO;
import com.ecommerce.emarket.security.services.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

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

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new ResponseEntity<>(new MessageResponse("Error: Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("Error: Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Create new user's account
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new APIException("Error: Role is not found."));

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));

                        roles.add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(sellerRole);

                    default:
                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity<>(new MessageResponse("User registered successfully!"), HttpStatus.OK);
    }
}
