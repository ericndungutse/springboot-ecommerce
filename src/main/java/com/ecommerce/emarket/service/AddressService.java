package com.ecommerce.emarket.service;

import java.util.List;

import com.ecommerce.emarket.payload.AddressDTO;
import com.ecommerce.emarket.payload.ApiResponse;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressById(Long addressId);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    ApiResponse deleteAddress(Long addressId);
}
