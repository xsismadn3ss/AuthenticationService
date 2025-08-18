package com.grupo3.authentication_service.encrypt;

import com.grupo3.authentication_service.common.dto.MessageDto;
import com.grupo3.authentication_service.encrypt.Dto.EncryptDto;
import com.grupo3.authentication_service.encrypt.service.IEncryptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encryption/")
public class EncryptController {

    private final IEncryptService encryptService;

    public EncryptController(IEncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @PostMapping
    public ResponseEntity<MessageDto> encrypt(@RequestBody @Valid EncryptDto encrypt){

        String encryptedValue = this.encryptService.encrypt(encrypt.getValue());

        MessageDto messageDto = new MessageDto();

        messageDto.setMessage(encryptedValue);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

}
