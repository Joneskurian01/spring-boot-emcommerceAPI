package com.joneskurian.api.store.dtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
// made a dto specific to update user because the fields in userdto might grow and not be relevant to update.
    private String name;
    private String email;
}
