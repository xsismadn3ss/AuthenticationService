package com.grupo3.authentication_service.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    @Schema(description = "Dominio al que se le generará un token de acceso")
    private String domain;

    @Schema(description = "Nombre de usuario")
    @NotBlank(message = "El campo username no puede estar vacío")
    @NotNull(message = "El campo username no puede ser nulo")
    private String username;

    @Schema(description = "Contraseña")
    @NotNull(message = "Password no puede ser nulo")
    @NotBlank(message = "Password no puede estar vacío")
    private String password;
}
