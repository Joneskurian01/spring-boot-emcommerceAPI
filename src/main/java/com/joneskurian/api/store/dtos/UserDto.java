package com.joneskurian.api.store.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    /*
When returning the dto as a json object like in an api you may want to change the dto further like renaming the
field or hiding more members. @JsonIgnore hides a member. @JsonProperty changes the name of the member in api calls.
@JsonInclude(JsonInclude.Include.NON_NULL) ensures the field only appears when the field is non-null.
     */
    @JsonProperty("user_id")
    private Long id;
    private String name;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phoneNumber;
}
