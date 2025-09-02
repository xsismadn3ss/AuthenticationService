package com.grupo3.authentication_service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public SimpleUserDto toSimpleUserDto() {
        return new SimpleUserDto(id, username, firstname, lastname, email);
    }
}
