package com.ecommerce.emarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.emarket.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
