package com.joneskurian.api.store.dtos;

import com.joneskurian.api.store.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterUserDto {
    @NotBlank(message = "name is required")// field cannot be "" " "
    @Size(max=255, message = "name must be less than 255 chars")
    private String name;
    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    @Lowercase //this is a custom validation that I implemented. look to validation package for more info.
    private String email;
    @NotBlank(message = "password is required")
    @Size(min=6,max=25,message="password must be between 6 and 25 chars")
    private String password;
}
