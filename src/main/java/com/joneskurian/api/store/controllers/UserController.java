package com.joneskurian.api.store.controllers;

import com.joneskurian.api.store.dtos.UserDto;
import com.joneskurian.api.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
/*
In most cases you do not want to expose you the members of an entity in an api therefore you use a dto to only
expose what is needed. Go to UserDto for more info.
 */
    @GetMapping("/")
    public Iterable<UserDto> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getId(),user.getName(),user.getEmail()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var user = userRepository.findById(id).orElse(null);
        if (user==null)
            return ResponseEntity.notFound().build();
        var userDto = new UserDto(user.getId(),user.getName(),user.getEmail()) ;
        return ResponseEntity.ok(userDto);
    }
}
