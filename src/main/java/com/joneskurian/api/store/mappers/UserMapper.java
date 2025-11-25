package com.joneskurian.api.store.mappers;

import com.joneskurian.api.store.dtos.RegisterUserDto;
import com.joneskurian.api.store.dtos.UpdateUserRequest;
import com.joneskurian.api.store.dtos.UserDto;
import com.joneskurian.api.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target="createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "name",source = "name"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target="id",source = "id")
    })
    UserDto toDto(User user);

    User registerUser(RegisterUserDto request);
//Below method is used to update existing users. When a put request is sent to api endpoint.
    void update(UpdateUserRequest request,@MappingTarget User user);
}
