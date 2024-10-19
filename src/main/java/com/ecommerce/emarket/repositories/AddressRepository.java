package com.ecommerce.emarket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.emarket.model.Address;
import com.ecommerce.emarket.model.User;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUserAndAddressId(User loggedInUser, Long addressId);

}
