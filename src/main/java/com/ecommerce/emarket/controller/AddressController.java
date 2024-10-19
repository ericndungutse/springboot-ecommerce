package com.ecommerce.emarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.emarket.payload.AddressDTO;
import com.ecommerce.emarket.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    AddressService addressService;

    @PostMapping("/users/addresses")
    public ResponseEntity<AddressDTO> addAddress(@Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO address = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    // @GetMapping("/users/addresses")
    // public ResponseEntity<List<AddressDTO>> getUserAddresses() {

    // List<AddressDTO> addresses = addressService.getUserAddresses();

    // return new ResponseEntity<>(addresses, HttpStatus.OK);
    // }

}
