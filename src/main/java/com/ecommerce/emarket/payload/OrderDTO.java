package com.ecommerce.emarket.payload;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String email;
    private LocalDate orderDate;
    private PaymentDTO payment;
    private double totalAmount;
    private String orderStatus;
    private Long addressId;
    private List<OrderItemDTO> orderItems;
}
