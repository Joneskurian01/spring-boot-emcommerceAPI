package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
