package com.ecommerce.emarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.emarket.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
