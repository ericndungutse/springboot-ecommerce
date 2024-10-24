package com.ecommerce.emarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.emarket.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
