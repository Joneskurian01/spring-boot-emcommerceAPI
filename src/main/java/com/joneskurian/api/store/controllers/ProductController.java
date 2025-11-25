package com.joneskurian.api.store.controllers;

import com.joneskurian.api.store.dtos.ProductDto;
import com.joneskurian.api.store.entities.Product;
import com.joneskurian.api.store.mappers.ProductMapper;
import com.joneskurian.api.store.repositories.CategoryRepository;
import com.joneskurian.api.store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private CategoryRepository categoryRepository;

    @GetMapping({"","/"})
    public Iterable<ProductDto> getProducts(
            @RequestParam(required = false,name="categoryid") Byte categoryId
    ){
        System.out.println(categoryId);
        List<Product> products;
        if (categoryId!= null)
            products= productRepository.findAllByCategory_id(categoryId);
        else
            products =productRepository.findAll();
        return products.stream().map(productMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> fetchProduct(
            @PathVariable(required = true) Long id
    ){
        var product = productRepository.findById(id).orElse(null);
        return product!=null?ResponseEntity.ok(productMapper.toDto(product)):ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<Object> createProduct(
            @RequestBody ProductDto request,
            UriComponentsBuilder uriBuilder){
        var cat = categoryRepository.findById(request.getCategoryId()).orElse(null);

        if(cat==null)
            return ResponseEntity.badRequest().body("product category not found");

        var product = productMapper.registerProduct(request);
        System.out.println(product);
        product.setCategory(cat);
        productRepository.save(product);

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(productMapper.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto request
    ){
        var cat = categoryRepository.findById(request.getCategoryId()).orElseThrow();

        if(!productRepository.existsById(id))
            return ResponseEntity.notFound().build();

        var product = productRepository.findById(id).orElseThrow();
        productMapper.update(request,product);
        product.setCategory(cat);
        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(name = "id") Long id
    ){
        return productRepository.existsById(id)?ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }
}
