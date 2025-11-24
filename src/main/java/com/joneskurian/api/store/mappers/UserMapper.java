package com.joneskurian.api.store.mappers;

import com.joneskurian.api.store.dtos.UserDto;
import com.joneskurian.api.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target="createdAt", expression = "java(java.time.LocalDateTime.now())"),
//            @Mapping(target = "name",source = "name"),
//            @Mapping(target = "email", source = "email"),
//            @Mapping(target="id",source = "id")
    })

    UserDto toDto(User user);
}
