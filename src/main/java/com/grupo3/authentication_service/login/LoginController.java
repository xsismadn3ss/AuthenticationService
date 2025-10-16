package com.grupo3.authentication_service.login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import shareddtos.usersmodule.auth.MessageDto;
import com.grupo3.authentication_service.encrypt.service.IEncryptService;
import com.grupo3.authentication_service.login.dto.LoginResponseDto;
import com.grupo3.authentication_service.login.dto.LoginUserDto;
import com.grupo3.authentication_service.token.service.ITokenService;
import shareddtos.usersmodule.auth.UserDto;
import com.grupo3.authentication_service.user.service.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class LoginController {
    @Value("${server.domain}")
    private String domain;
    private final IUserService userService;
    private final IEncryptService encryptService;
    private final ITokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginUserDto loginUserDto,
            HttpServletResponse response) {
        // buscar usuario
        UserDto userDto = this.userService.findByUsername(loginUserDto.getUsername());

        // validar contraseña
        if(!this.encryptService.compare(loginUserDto.getPassword(), userDto.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña invalida");
        }

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("username", userDto.getUsername());
        payload.put("id", userDto.getId());
        String token = this.tokenService.generateToken(payload, loginUserDto.getUsername());

        Cookie cookie = new Cookie("token", token);
        cookie.setDomain(domain); // añadir dominio a la cookie
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        cookie.setSecure(true);
        response.addCookie(cookie);

        LoginResponseDto message = new LoginResponseDto(
                "Has iniciado sesión correctamente",
                token,
                userDto.getUsername());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageDto> logout(
            HttpServletResponse response,
            @CookieValue(value = "token", required = false) String token){
        MessageDto messageDto = new MessageDto();

        if(token == null || token.isEmpty()){
            messageDto.setMessage("No has iniciado sesión");
            return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
        }

        Cookie cookie = new Cookie("token", "");
        cookie.setDomain(domain);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);
        response.addCookie(cookie);
        messageDto.setMessage("Has cerrado sesión correctamente");
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }
}