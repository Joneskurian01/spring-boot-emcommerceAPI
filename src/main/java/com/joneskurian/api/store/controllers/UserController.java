package com.joneskurian.api.store.controllers;

import com.joneskurian.api.store.dtos.UserDto;
import com.joneskurian.api.store.mappers.UserMapper;
import com.joneskurian.api.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
/*
In most cases you do not want to expose you the members of an entity in an api therefore you use a dto to only
expose what is needed. Go to UserDto for more info.
 */
    @GetMapping("/")
    public Iterable<UserDto> getAllUser(
            @RequestParam(required = false) String sort
    ){
        Sort sortOrder=(sort!=null && !Set.of("name", "email").contains(sort.toLowerCase()))?
                Sort.by(sort.toLowerCase()):
                Sort.unsorted();

         var users = userRepository.findAll(sortOrder);
         return  users.stream()
                 .map(userMapper::toDto)
                 .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var user = userRepository.findById(id).orElse(null);
        if (user==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
