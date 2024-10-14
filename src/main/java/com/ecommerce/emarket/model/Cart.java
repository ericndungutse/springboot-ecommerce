package com.ecommerce.emarket.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // mappedBy:Used onthe inverseside ofa bidirectionalrelationship.It specifiesthe
    // ownerof therelationship bypointing tothe field in the owning entity that
    // manages the relationship. The entity with the mappedBy attribute does not own
    // the foreign key.
    @OneToMany(mappedBy = "cart", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    // @JoinColumn: Specifies the
    // foreign key
    // column in
    // the owning
    // entity (the entity that "owns" the relationship). It is used when this entity
    // holds the foreign key that references another entity.
    private List<CartItem> cartItems = new ArrayList<>();

    private Double totalPrice = 0.0;

}
