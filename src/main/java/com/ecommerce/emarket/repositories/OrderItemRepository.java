package com.ecommerce.emarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.emarket.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
