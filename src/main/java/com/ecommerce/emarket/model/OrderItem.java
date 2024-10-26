package com.ecommerce.emarket.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private double discount;

    private double orderedProductPrice;

}
