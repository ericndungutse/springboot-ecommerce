package com.ecommerce.emarket.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "username")
    private String username;
    @NotBlank
    @Email
    @Size(max = 50)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(min = 6, max = 120)
    private String password;

    // Cascade User operation from user to roles. Needs a modification on user
    // setter method to add roles to user object and vice versa
    // THe only cascadded ops is persist and merge, which means that when a user
    // being saved comes with roles that are not in the db, they will be saved in
    // the db as well as the user object itself
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // A user will have products if he or she is a seller
    // OrpanRemoval is used to delete the product if the user is deleted
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<Product> products;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_addresses", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses = new ArrayList<>();

    // Avoiding circular reference
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true)
    private Cart cart;

    public User(String username, String email,
            String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
