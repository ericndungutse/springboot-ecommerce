package com.ecommerce.emarket.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.emarket.exceptions.APIException;
import com.ecommerce.emarket.exceptions.ResourceNotFoundException;
import com.ecommerce.emarket.model.Address;
import com.ecommerce.emarket.model.Cart;
import com.ecommerce.emarket.model.CartItem;
import com.ecommerce.emarket.model.Order;
import com.ecommerce.emarket.model.OrderItem;
import com.ecommerce.emarket.model.Payment;
import com.ecommerce.emarket.model.Product;
import com.ecommerce.emarket.payload.CartDTO;
import com.ecommerce.emarket.payload.OrderDTO;
import com.ecommerce.emarket.payload.OrderItemDTO;
import com.ecommerce.emarket.repositories.AddressRepository;
import com.ecommerce.emarket.repositories.CartRepository;
import com.ecommerce.emarket.repositories.OrderItemRepository;
import com.ecommerce.emarket.repositories.OrderRepository;
import com.ecommerce.emarket.repositories.PaymentRepository;
import com.ecommerce.emarket.repositories.ProductRepository;

import ch.qos.logback.core.model.Model;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Override
    @Transactional
    public OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId,
            String pgStatus, String pgResponseMessage) {

        // Cart cart = cartRepository.findCartByEmail(emailId).orElseThrow(
        // () -> new ResourceNotFoundException("Cart", "email", "emailId"));

        // Address address = addressRepository.findById(addressId).orElseThrow(
        // () -> new ResourceNotFoundException("Address", "addresId", addressId));

        // Order order = new Order();
        // order.setEmail(emailId);
        // order.setOrderDate(LocalDate.now());
        // order.setTotalAmount(cart.getTotalPrice());
        // order.setOrderStatus("Order Accepted !");
        // order.setAddress(address);

        // Payment payment = new Payment(order, paymentMethod, pgPaymentId, pgStatus,
        // pgResponseMessage, pgName);
        // payment.setOrder(order);
        // payment = paymentRepository.save(payment);
        // order.setPayment(payment);

        // Order savedOrder = orderRepository.save(order);

        // List<CartItem> cartItems = cart.getCartItems();

        // if (cartItems.isEmpty()) {
        // throw new APIException("Cart is empty");
        // }

        // List<OrderItem> orderItems = new ArrayList<>();

        // for (CartItem cartItem : cartItems) {
        // OrderItem orderItem = new OrderItem();
        // orderItem.setOrder(savedOrder);
        // // orderItem.setProduct(cartItem.getProduct());
        // orderItem.setQuantity(cartItem.getQuantity());
        // orderItem.setDiscount(cartItem.getDiscount());
        // orderItem.setOrderedProductPrice(cartItem.getProductPrice());
        // orderItems.add(orderItem);
        // }

        // savedOrder.setOrderItems(orderItems);
        // orderItemRepository.saveAll(orderItems);

        // // Update Stock
        // cart.getCartItems().forEach((item) -> {
        // int quantity = item.getQuantity();
        // Product product = item.getProduct();

        // product.setQuantity(product.getQuantity() - quantity);
        // productRepository.save(product);

        // });

        // // Clear Cart
        // cartService.clearMyCart();

        // OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        // orderItems.forEach((orderItem) -> {
        // orderDTO.getOrderItems().add(modelMapper.map(orderItem, OrderItemDTO.class));
        // });

        return new OrderDTO();
    }

}
