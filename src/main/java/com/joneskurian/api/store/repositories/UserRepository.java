package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
