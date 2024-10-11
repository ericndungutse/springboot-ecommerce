package com.ecommerce.emarket.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Street must be atleast 5 characters")
    private String street;

    @NotBlank
    @Size(min = 5, message = "Street must be atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 5, message = "City must be atleast 5 characters")
    private String city;

    @NotBlank
    @Size(min = 5, message = "State must be atleast 5 characters")
    private String state;

    @NotBlank
    @Size(min = 6, message = "Pincode must be atleast 6 characters")
    private String pincode;

    // Mapped by specify the owner of the relationship
    // From lombok, to avoid circular relationship
    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> user = new ArrayList<>();

    public Address(@NotBlank @Size(min = 5, message = "Street must be atleast 5 characters") String street,
            @NotBlank @Size(min = 5, message = "Street must be atleast 5 characters") String buildingName,
            @NotBlank @Size(min = 5, message = "City must be atleast 5 characters") String city,
            @NotBlank @Size(min = 5, message = "State must be atleast 5 characters") String state,
            @NotBlank @Size(min = 6, message = "Pincode must be atleast 6 characters") String pincode,
            List<User> user) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.user = user;
    }

}
