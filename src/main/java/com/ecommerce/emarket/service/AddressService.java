package com.ecommerce.emarket.service;

import java.util.List;

import com.ecommerce.emarket.payload.AddressDTO;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddresses();
}
