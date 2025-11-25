package com.joneskurian.api.store.controllers;

import com.joneskurian.api.store.dtos.ChangePasswordRequest;
import com.joneskurian.api.store.dtos.RegisterUserDto;
import com.joneskurian.api.store.dtos.UpdateUserRequest;
import com.joneskurian.api.store.dtos.UserDto;
import com.joneskurian.api.store.mappers.UserMapper;
import com.joneskurian.api.store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
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

For simple validations you should attatch it to the dto. For more complex logic specific to your application it is best to
do the validation inside the controller. For example, when registering a user you need to make sure the email is unique.
 */
    @GetMapping("/")
    public Iterable<UserDto> getAllUser(
            @RequestParam(required = false) String sort
    ){
        System.out.println(sort);
        Sort sortOrder=(sort!=null && !Set.of("name", "email","id").contains(sort.toLowerCase()))?
                Sort.by(sort.toLowerCase()).descending():
                Sort.unsorted();

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sortOrder);

         return userRepository.findAllUserDto()
                 .stream()
                 .peek(u-> u.setCreatedAt(java.time.LocalDateTime.now()))
                 .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var user = userRepository.findUserDtoById(id).orElse(null);
        if (user==null)
            return ResponseEntity.notFound().build();
        user.setCreatedAt(java.time.LocalDateTime.now());
        return ResponseEntity.ok(user);
    }

    @PostMapping({"","/"})
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterUserDto request,
            UriComponentsBuilder uriBuilder){

        if (userRepository.existsByEmail(request.getEmail()))
            return ResponseEntity.badRequest().body(
                    Map.of("email","email is already registered")
            );

        var user =  userMapper.registerUser(request);
        userRepository.save(user);
        UserDto userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name="id") Long id,
            @RequestBody UpdateUserRequest userRequest){
          var user = userRepository.findById(id).orElse(null);
          if(user==null){
              return  ResponseEntity.notFound().build();
          }

          userMapper.update(userRequest,user);
          userRepository.save(user);
          return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(name="id")  Long id
        ){
        if(!userRepository.existsById(id)){
            return  ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable(required = true,name = "id") Long id,
            @RequestBody ChangePasswordRequest request){
        var user = userRepository.findById(id).orElse(null);
        if(user == null)
            return ResponseEntity.notFound().build();
        //need to check the old password provided matches else it is a bad request.
        if (!user.getPassword().equals(request.getOldPassword()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }
}
