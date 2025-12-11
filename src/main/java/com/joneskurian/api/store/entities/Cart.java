package com.joneskurian.api.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Table(name="carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date_created",insertable = false,updatable = false)
    private LocalDate dateCreated;

    @OneToMany( mappedBy = "cart",
                cascade = {CascadeType.PERSIST,CascadeType.MERGE},
                orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Set<CartItem> products = new LinkedHashSet<>();

    public void addItem(CartItem item){
        item.setCart(this);
        this.products.add(item);
    }

    public void removeItem(CartItem item){
        item.setCart(null);
        this.products.remove(item);
    }

    public CartItem setProduct(Product product, Integer amount){
        var newItem = CartItem.builder()
                .product(product)
                .cart(this)
                .quantity(amount)
                .build();
        //person could want to remove or
        if(this.products.contains(newItem)){
            var prod= this.products.stream()
                    .filter(u->u.equals(newItem))
                    .findFirst()
                    .get();
            if(prod.getQuantity()+amount>=0) prod.setQuantity(prod.getQuantity()+amount);
            else throw new IllegalArgumentException("negative amount provided exceeds current quantity");
            return prod;
        }else if (amount>=0){
            this.addItem(newItem);
            return newItem;
        }else{
            throw new IllegalArgumentException("negative amount provided exceeds current quantity");
        }

    }

    public CartItem setProduct(Product product, Integer amount,int flag){
        //flag denotes whether we want to set to amount or add amount to quantity. 1 means set instead of add
        var newItem = CartItem.builder()
                .product(product)
                .cart(this)
                .quantity(amount)
                .build();
        //person could want to remove or
        if(this.products.contains(newItem)){
            var prod= this.products.stream()
                    .filter(u->u.equals(newItem))
                    .findFirst()
                    .get();

            if(flag!=1){
                if(prod.getQuantity()+amount>=0) prod.setQuantity(prod.getQuantity()+amount);
                else throw new IllegalArgumentException("negative amount provided exceeds current quantity");
            }else {
                if (amount<=0) throw new IllegalArgumentException("negative amount provided exceeds current quantity");
                prod.setQuantity(amount);
            }
            return prod;
        }else if (amount>0){ //flag does not make an impact here setting and adding would be identical where initial quantity is 0.
            this.addItem(newItem);
            return newItem;
        }else{
            throw new IllegalArgumentException("negative amount provided exceeds current quantity");
        }
    }

    @Transient
    public BigDecimal getTotalPrice(){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(CartItem cartItem:this.getProducts()){
            totalPrice= totalPrice.add(cartItem.getTotalPrice()) ;
        }
        return totalPrice;
    }
}
