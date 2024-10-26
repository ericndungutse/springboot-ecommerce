package com.ecommerce.emarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @NotBlank
    @Size(min = 5, message = "Country must be atleast 5 characters")
    private String country;

    // Mapped by specify the owner of the relationship
    // From lombok, to avoid circular relationship

    // prevent stackoverflow error/ infinite loop/ circular reference

    // When thetoString() method is called on a Person object with a circular
    // reference, it will try to convert the parent field to a string. However, to
    // convert the parent field to a string, the toString() method of the parent
    // object will be called, which in turn will try to convert its own parent field
    // to a string, and so on. This creates an infinite loop.
    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

}
