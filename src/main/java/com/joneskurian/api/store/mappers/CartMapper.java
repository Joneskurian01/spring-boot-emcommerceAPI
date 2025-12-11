package com.joneskurian.api.store.mappers;

import com.joneskurian.api.store.dtos.CartDto;
import com.joneskurian.api.store.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",uses = {CartItemMapper.class})
public interface CartMapper {
    @Mappings({
            @Mapping(target = "items", source = "products"),
            @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    })
    CartDto toDto(Cart cart);

}
