package com.grupo3.authentication_service.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    @Schema(description = "Dominio al que se le generará un token de acceso")
    private String domain;
    private String username;
    private String password;
}
