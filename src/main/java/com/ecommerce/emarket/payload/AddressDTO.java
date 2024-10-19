package com.ecommerce.emarket.payload;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
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
    private String country;

    @NotBlank
    @Size(min = 6, message = "Pincode must be atleast 6 characters")
    private String pincode;

}
