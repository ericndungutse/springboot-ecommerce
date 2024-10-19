package com.ecommerce.emarket.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.emarket.exceptions.ResourceNotFoundException;
import com.ecommerce.emarket.model.Address;
import com.ecommerce.emarket.model.User;
import com.ecommerce.emarket.payload.AddressDTO;
import com.ecommerce.emarket.repositories.AddressRepository;
import com.ecommerce.emarket.repositories.UserRepository;
import com.ecommerce.emarket.utils.AuthUtil;

import jakarta.annotation.Resource;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {

        User user = authUtil.loggedInUser();
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);
        addressRepository.save(address);

        List<Address> addressList = user.getAddresses();
        user.setAddresses(addressList);
        userRepository.save(user);

        return modelMapper.map(addressRepository.save(address), AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddresses() {
        User user = authUtil.loggedInUser();
        List<Address> addresses = user.getAddresses();
        return addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        return modelMapper.map(address, AddressDTO.class);
    }

}
