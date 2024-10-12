package com.ecommerce.emarket.security.response;

import java.util.List;

public class LoginResponseDTO {
    private Long id;
    private String jwt;
    private String username;
    private List<String> roles;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(Long id, String jwt, String username, List<String> roles) {
        this.id = id;
        this.jwt = jwt;
        this.username = username;
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(String role) {
        this.roles.addLast(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
