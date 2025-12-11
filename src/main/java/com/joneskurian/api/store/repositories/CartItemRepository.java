package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem,Long> {
}
