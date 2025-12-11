package com.joneskurian.api.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private UUID id;

    private Set<CartItemDto> items= new HashSet<>();

    private BigDecimal totalPrice= BigDecimal.ZERO;
}

