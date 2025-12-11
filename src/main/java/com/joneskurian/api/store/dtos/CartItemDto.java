package com.joneskurian.api.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private ProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
