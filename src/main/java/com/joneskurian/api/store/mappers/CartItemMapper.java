package com.joneskurian.api.store.mappers;

import com.joneskurian.api.store.dtos.CartItemDto;
import com.joneskurian.api.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {ProductMapper.class})
public interface CartItemMapper {
    @Mapping(target="totalPrice" , expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
