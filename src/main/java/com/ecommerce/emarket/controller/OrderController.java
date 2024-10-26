package com.ecommerce.emarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.emarket.model.Order;
import com.ecommerce.emarket.payload.OrderDTO;
import com.ecommerce.emarket.payload.OrderRequestDTO;
import com.ecommerce.emarket.service.OrderService;
import com.ecommerce.emarket.utils.AuthUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/orders/users/{userId}/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(
            @PathVariable Long userId,
            @PathVariable String paymentMethod, @Valid @RequestBody OrderRequestDTO orderRquestDTO) {

        String emailId = authUtil.loggedInEmail();

        OrderDTO order = orderService.placeOrder(
                emailId,
                orderRquestDTO.getAddressId(),
                paymentMethod,
                orderRquestDTO.getPgName(),
                orderRquestDTO.getPgPaymentId(),
                orderRquestDTO.getPgStatus(),
                orderRquestDTO.getPgResponseMessage());
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
