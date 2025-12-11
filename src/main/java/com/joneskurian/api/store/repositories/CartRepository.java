package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends CrudRepository<Cart, UUID> {

    @Query("Select c FROM Cart c LEFT JOIN FETCH c.products cp " +
            "LEFT JOIN FETCH cp.product p WHERE c.id= :id")
    Optional<Cart> findByIdEager(@Param("id") UUID id);
}
