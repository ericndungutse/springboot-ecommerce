package com.ecommerce.emarket.service;

import com.ecommerce.emarket.payload.OrderDTO;

public interface OrderService {

    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId,
            String pgStatus, String pgResponseMessage);

}