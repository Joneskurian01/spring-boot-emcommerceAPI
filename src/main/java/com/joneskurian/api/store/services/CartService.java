package com.joneskurian.api.store.services;

import com.joneskurian.api.store.dtos.CartDto;
import com.joneskurian.api.store.dtos.CartItemDto;
import com.joneskurian.api.store.entities.Cart;
import com.joneskurian.api.store.entities.CartItem;
import com.joneskurian.api.store.entities.Product;
import com.joneskurian.api.store.exceptions.BadRequest;
import com.joneskurian.api.store.exceptions.CartNotFound;
import com.joneskurian.api.store.exceptions.ProductNotFound;
import com.joneskurian.api.store.mappers.CartItemMapper;
import com.joneskurian.api.store.mappers.CartMapper;
import com.joneskurian.api.store.mappers.ProductMapper;
import com.joneskurian.api.store.repositories.CartRepository;
import com.joneskurian.api.store.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductMapper productMapper;
    private ProductRepository productRepository;
    private CartItemMapper cartItemMapper;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addProduct(UUID cartId,Long productId){
        if(!cartRepository.existsById(cartId))  throw new CartNotFound();
        if(!productRepository.existsById(productId)) throw new ProductNotFound();

        var cart= cartRepository.findByIdEager(cartId).orElseThrow();
        var prod = productRepository.findById(productId).orElseThrow();

        CartItem result = cart.setProduct(prod,1);
        cartRepository.save(cart);

        return cartItemMapper.toDto(result);
    }

    public CartItemDto addProduct(UUID cartId, Long productId, Integer quantity, int flag) {
        //flag indicates whether you are setting or adding the quantity.
        var cart = cartRepository.findByIdEager(cartId).orElse(null);
        var product = productRepository.findById(productId).orElse(null);

        if(product == null || cart==null) throw new BadRequest();

        CartItem result = cart.setProduct(product,quantity,flag); //want to set the quantity of the product in cart to the quantity in request body.

        cartRepository.save(cart);
        return cartItemMapper.toDto(result);
    }

    public boolean deleteProduct(UUID cartid, Long productid){
        Cart cart;
        Product product;
        if(cartRepository.existsById(cartid) && productRepository.existsById(productid)){

            cart =cartRepository.findByIdEager(cartid).orElseThrow();
            product = productRepository.findById(productid).orElseThrow();

            if(cart.getProducts().stream().anyMatch(u->u.getProduct().equals(product))){

                cart.removeItem(CartItem.builder().cart(cart).product(product).build());
                cartRepository.save(cart);
                return true;
            }
        }
        return false;
    }
}
