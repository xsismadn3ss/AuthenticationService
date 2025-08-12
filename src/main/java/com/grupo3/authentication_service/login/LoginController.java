package com.grupo3.authentication_service.login;

import com.grupo3.authentication_service.common.dto.ErrorDto;
import com.grupo3.authentication_service.common.dto.MessageDto;
import com.grupo3.authentication_service.encrypt.service.IEncryptService;
import com.grupo3.authentication_service.login.dto.LoginUserDto;
import com.grupo3.authentication_service.token.service.ITokenService;
import com.grupo3.authentication_service.user.dto.UserDto;
import com.grupo3.authentication_service.user.service.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/authentication/")
public class LoginController {
    private final IUserService userService;
    private final IEncryptService encryptService;
    private final ITokenService tokenService;

    public LoginController(IUserService userService, IEncryptService encryptService, ITokenService tokenService) {
        this.userService = userService;
        this.encryptService = encryptService;
        this.tokenService = tokenService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto, HttpServletResponse response) {
        try{
            // buscar usuario
            UserDto userDto = this.userService.findByUsername(loginUserDto.getUsername());

            // validar contraseña
            if(!this.encryptService.compare(loginUserDto.getPassword(), userDto.getPassword())){
                ErrorDto errorDto = new ErrorDto("No se pudo iniciar sesión", "Contraseña incorrecta");
                return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
            }

            HashMap<String, Object> payload = new HashMap<>();
            payload.put("username", userDto.getUsername());
            payload.put("id", userDto.getId());
            String token = this.tokenService.generateToken(payload, loginUserDto.getUsername());

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setPath("/");
            cookie.setSecure(true);
            response.addCookie(cookie);

            MessageDto message = new MessageDto("Has iniciado sesión correctamente");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (Exception e){
            ErrorDto errorDto = new ErrorDto("No se pudo iniciar sesión", String.valueOf(e.getCause()));
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletResponse response, @CookieValue("token") String token){
        MessageDto messageDto = new MessageDto();

        if(token == null || token.isEmpty()){
            messageDto.setMessage("No has iniciado sesión");
            return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
        }

        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);
        response.addCookie(cookie);
        messageDto.setMessage("Has cerrado sesión correctamente");
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PostMapping("validate")
    public ResponseEntity<?> validate(@CookieValue("token") String token){
        try{
            if (token == null || token.isEmpty()) {
                MessageDto messageDto = new MessageDto("No has iniciado sesión");
                return new ResponseEntity<>(messageDto, HttpStatus.UNAUTHORIZED);
            }
            String username = this.tokenService.extractUsername(token);
            UserDto userDto = this.userService.findByUsername(username);
            userDto.setPassword(null);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto("No se pudo validar", String.valueOf(e.getCause()));
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
    }

}
