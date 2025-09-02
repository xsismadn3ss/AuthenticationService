package com.grupo3.authentication_service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDto {
    private Long id;
    private String username;
    private String name;
    private String lastname;
    private String email;
}
