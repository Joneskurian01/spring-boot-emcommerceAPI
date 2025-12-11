package com.joneskurian.api.store.controllers;


import com.joneskurian.api.store.dtos.AddProductDto;
import com.joneskurian.api.store.dtos.CartDto;
import com.joneskurian.api.store.dtos.CartItemDto;
import com.joneskurian.api.store.dtos.UpdateCartRequestDto;
import com.joneskurian.api.store.exceptions.BadRequest;
import com.joneskurian.api.store.exceptions.CartNotFound;
import com.joneskurian.api.store.exceptions.ProductNotFound;
import com.joneskurian.api.store.mappers.CartItemMapper;
import com.joneskurian.api.store.mappers.CartMapper;
import com.joneskurian.api.store.repositories.CartItemRepository;
import com.joneskurian.api.store.repositories.CartRepository;
import com.joneskurian.api.store.repositories.ProductRepository;
import com.joneskurian.api.store.services.CartService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private CartMapper cartMapper;
    private CartItemMapper cartItemMapper;
    private CartService cartService;

    @PostMapping("/")
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriComponent){
        var newCart = cartService.createCart();
        var uri = uriComponent.path("carts/{cartid}").buildAndExpand(newCart.getId()).toUri();
        return ResponseEntity.created(uri).body(newCart);
    }

    @GetMapping("/{cartid}")
    @Transactional
    public ResponseEntity<CartDto> getCart(
            @PathVariable(name = "cartid") UUID cartId
    ){
        var cart = cartRepository.findByIdEager(cartId).orElse(null);
        if(cart==null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    @PostMapping("/{cartid}/items")
    public ResponseEntity<CartItemDto> addProduct(
            @PathVariable(name = "cartid") UUID cartId,
            @RequestBody AddProductDto request
    ){
        CartItemDto result = cartService.addProduct(cartId,request.getProductId());

      return ResponseEntity.ok(result);
    }

    @PutMapping("/{cartid}/items/{productid}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable(name = "cartid") UUID cartId,
            @PathVariable(name = "productid") Long productId,
            @RequestBody @Valid UpdateCartRequestDto request
    ){
        CartItemDto result = cartService.addProduct(cartId,productId,request.getQuantity(),1);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{cartid}/items/{productid}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable(name = "cartid") UUID cartid,
            @PathVariable(name = "productid") Long productid){

        return cartService.deleteProduct(cartid,productid)?ResponseEntity.noContent().build():
                ResponseEntity.badRequest()
                        .body("the productid or cartid does not exist or product has already been removed from cart");
    }

    @DeleteMapping("/{cartid}/items")
    public ResponseEntity<?> deleteAll(@PathVariable(name = "cartid") UUID cartid){
        var cart = cartRepository.findByIdEager(cartid).orElse(null);

        if(cart==null) return ResponseEntity.badRequest().body("cartid does not exist");

        cart.getProducts().clear();

        cartRepository.save(cart);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFound.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","cart not found"));
    }

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","product not found"));
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<Map<String, String>> handBadRequest(){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","the cart or product does not exist"));
    }
}
