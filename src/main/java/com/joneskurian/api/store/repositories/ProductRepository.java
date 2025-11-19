package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}