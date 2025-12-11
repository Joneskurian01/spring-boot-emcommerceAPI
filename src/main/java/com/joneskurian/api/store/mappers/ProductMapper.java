package com.joneskurian.api.store.mappers;

import com.joneskurian.api.store.dtos.ProductDto;
import com.joneskurian.api.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({
            @Mapping(target = "categoryId",source = "category.id"),
            @Mapping(target="name", source = "name"),
            @Mapping(target="id", source = "id"),
            @Mapping(target="description", source = "description"),
            @Mapping(target="price", source = "price")
    })
    ProductDto toDto(Product product);

    Product registerProduct(ProductDto product);

    @Mapping(target="id",ignore = true)
    void update(ProductDto productDto,@MappingTarget Product product);
}
