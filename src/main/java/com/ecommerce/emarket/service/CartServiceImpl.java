package com.ecommerce.emarket.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.emarket.exceptions.APIException;
import com.ecommerce.emarket.exceptions.ResourceNotFoundException;
import com.ecommerce.emarket.model.Cart;
import com.ecommerce.emarket.model.CartItem;
import com.ecommerce.emarket.model.Product;
import com.ecommerce.emarket.payload.CartDTO;
import com.ecommerce.emarket.payload.ProductDTO;
import com.ecommerce.emarket.repositories.CartItemRepository;
import com.ecommerce.emarket.repositories.CartRepository;
import com.ecommerce.emarket.repositories.ProductRepository;
import com.ecommerce.emarket.utils.AuthUtil;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    // A class that helps work with auth related tasks
    AuthUtil authUtil;

    @Override
    @Transactional
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        // FInd Existing Cart or create one
        Cart cart = createCart();

        // Retrieve Product Details
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        // Perform Validations(Quantity, Stock, etc)
        CartItem cartItem = cartItemRepository
                .findCartItemByProductIdAndCartId(product.getProductId(),
                        cart.getCartId());

        if (cartItem != null) {
            throw new APIException("Product already in cart");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Product out of stock");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Order quantity exceeds available stock");
        }

        // Create a Cart Item
        CartItem newCartItem = new CartItem();

        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        // Save Cart item
        cartItemRepository.save(newCartItem);
        // Update Product Quantity
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        // Update Cart Total Price
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        // List<CartItem> cartItems = cart.getCartItems();
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCartId(cart.getCartId());

        System.out.println(cartItems);

        List<ProductDTO> cartProducts = cartItems.stream().map(item -> {
            ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
            productDTO.setQuantity(item.getQuantity());
            return productDTO;
        }).toList();

        cartDTO.setProducts(cartProducts);

        // Return Cart DTO
        return cartDTO;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());

        if (userCart != null) {
            return userCart;
        }

        Cart cart = new Cart();

        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart = cartRepository.save(cart);
        return newCart;

    }

    @Override
    public CartDTO getMyCart() {
        // Will load cart by user email
        Cart cart = cartRepository.findCartByEmail(authUtil.loggedInEmail());

        if (cart == null) {
            throw new APIException("Cart not found");
        }

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();
        cartDTO.setProducts(cartItems.stream().map(item -> {
            ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
            productDTO.setQuantity(item.getQuantity());
            return productDTO;
        }).toList());

        return cartDTO;

    }

    @Override
    // If anything happens, the transaction will be rolled back
    @jakarta.transaction.Transactional
    public CartDTO updateCartProductQuantity(Long productId, Integer quantity) {
        // Find Cart
        Cart cart = cartRepository.findCartByEmail(authUtil.loggedInEmail());

        if (cart == null) {
            throw new APIException("Cart not found");
        }

        // Find cartItem where productId and cartId
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cart.getCartId());

        if (cartItem == null) {
            throw new ResourceNotFoundException("CartItem", "productId", productId);
        }
        // Update Quantity
        cartItem.setQuantity(quantity);

        // save cartItem
        cartItemRepository.save(cartItem);

        // Update Cart Total Price
        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProductPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);

        // Save Cart
        cartRepository.save(cart);

        // Return CartDTO
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();
        cartDTO.setProducts(cartItems.stream().map(item -> {
            ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
            productDTO.setQuantity(item.getQuantity());
            return productDTO;
        }).toList());

        return cartDTO;

    }
}
