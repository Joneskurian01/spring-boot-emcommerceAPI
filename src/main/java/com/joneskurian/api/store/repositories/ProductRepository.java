package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p join fetch p.category c where c.Id = :categoryId")
    List<Product> findAllByCategory_id(@Param( "categoryId") Byte categoryId);

    Optional<Product> findById(Long id);
}