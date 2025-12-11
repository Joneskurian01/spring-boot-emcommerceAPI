package com.joneskurian.api.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Table(name="cart_items",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cart_id", "product_id"})
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CartItem {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @Setter(AccessLevel.PACKAGE)
    private Cart cart;

    @Transient
    public BigDecimal getTotalPrice(){
        if(product==null||quantity==null) return null;

        return this.product.getPrice()
                .multiply(BigDecimal.valueOf(this.quantity));
    }

    @Override
    public int hashCode(){
        return Objects.hash(product,cart);
    }

    @Override
    public boolean equals(Object obj){
        if (this==obj) return true;

        if(obj==null || getClass()!= obj.getClass()) return false;

        CartItem other = (CartItem) obj;

        return ((Objects.equals(product,other.product)) && (Objects.equals(cart,other.cart)));
    }
}
