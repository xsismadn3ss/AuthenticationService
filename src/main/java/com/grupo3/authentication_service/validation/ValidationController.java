package com.grupo3.authentication_service.validation;

import com.grupo3.authentication_service.token.service.ITokenService;
import shareddtos.usersmodule.auth.SimpleUserDto;
import com.grupo3.authentication_service.user.service.IUserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("${app.prefix}/validation/")
public class ValidationController {
    private final ITokenService tokenService;
    private final IUserService userService;

    public ValidationController(ITokenService tokenService, IUserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @GetMapping("cookie")
    public ResponseEntity<SimpleUserDto> validateCookie(
            @Parameter(description = "Token de acceso", in = ParameterIn.COOKIE)
            @CookieValue(value = "token", required = false) String token
    ){
        if(token == null || token.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token invalido");
        }
        this.tokenService.validateToken(token);
        String username = this.tokenService.extractUsername(token);
        SimpleUserDto simpleUser = this.userService.findByUsername(username).toSimpleUserDto();
        return new ResponseEntity<>(simpleUser, HttpStatus.OK);
    }

    @GetMapping("header")
    public ResponseEntity<SimpleUserDto> validateHeader(
            @Parameter(description = "Token de acceso", in = ParameterIn.HEADER)
            @RequestHeader(value = "Authorization", required = false) String authorization
    ){
        if(authorization == null || authorization.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token invalido");
        }else if(!authorization.startsWith("Bearer ")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token invalido, debe empezar por Bearer");
        }
        this.tokenService.validateToken(authorization.replace("Bearer ", ""));
        String username = this.tokenService.extractUsername(authorization.replace("Bearer ", ""));
        SimpleUserDto simpleUserDto = this.userService.findByUsername(username).toSimpleUserDto();
        return new ResponseEntity<>(simpleUserDto, HttpStatus.OK);
    }
}
