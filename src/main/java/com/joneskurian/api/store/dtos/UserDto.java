package com.joneskurian.api.store.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;//this field is created at returned at runtime look to UserMapper file for impl
// details.

    public void setCreatedAt(LocalDateTime time){
        this.createdAt=time;
    }
}
