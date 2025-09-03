package com.grupo3.authentication_service.encrypt;

import shareddtos.usersmodule.auth.MessageDto;
import shareddtos.usersmodule.auth.EncryptDto;
import com.grupo3.authentication_service.encrypt.service.IEncryptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/encryption")
public class EncryptController {

    private final IEncryptService encryptService;

    public EncryptController(IEncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @PostMapping
    public ResponseEntity<MessageDto> encrypt(@RequestBody EncryptDto encrypt) {

        if (encrypt == null || encrypt.getValue() == null || encrypt.getValue().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El valor a encriptar no puede ser nulo o vacío");
        }

        String encryptedValue = encryptService.encrypt(encrypt.getValue());

        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(encryptedValue);

        return ResponseEntity.ok(messageDto);
    }
}