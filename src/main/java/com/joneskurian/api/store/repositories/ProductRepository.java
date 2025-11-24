package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.category.Id = :categoryId")
    List<Product> findAllByCategory_id(@Param( "categoryId") Byte categoryId);
}