package com.joneskurian.api.store.controllers;

import com.joneskurian.api.store.dtos.ProductDto;
import com.joneskurian.api.store.entities.Product;
import com.joneskurian.api.store.mappers.ProductMapper;
import com.joneskurian.api.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping({"","/","/{categoryId}"})
    public Iterable<ProductDto> getProducts(
            @PathVariable(required = false) Byte categoryId
    ){
        System.out.println(categoryId);
        List<Product> products;
        if (categoryId!= null)
            products= productRepository.findAllByCategory_id(categoryId);
        else
            products =productRepository.findAll();
        return products.stream().map(productMapper::toDto).toList();
    }
}
