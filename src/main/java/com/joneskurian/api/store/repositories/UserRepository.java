package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.dtos.UserDto;
import com.joneskurian.api.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select new com.joneskurian.api.store.dtos.UserDto(u.id,u.name,u.email) from User u where u.id= :id ")
    Optional<UserDto> findUserDtoById(@Param("id") Long id);

    @Query("Select new com.joneskurian.api.store.dtos.UserDto(u.id,u.name,u.email) from User u")
    List<UserDto> findAllUserDto();

    boolean existsByEmail( String email);
}
