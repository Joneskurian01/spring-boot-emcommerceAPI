package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}