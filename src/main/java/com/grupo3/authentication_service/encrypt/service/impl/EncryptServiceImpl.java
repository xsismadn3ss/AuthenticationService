package com.grupo3.authentication_service.encrypt.service.impl;

import com.grupo3.authentication_service.encrypt.service.IEncryptService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptServiceImpl implements IEncryptService {
    private final PasswordEncoder passwordEncoder;

    public EncryptServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String password) {
        return this.passwordEncoder.encode(password);
    }

    @Override
    public Boolean compare(String password, String encryptedPassword) {
        return this.passwordEncoder.matches(password, encryptedPassword);
    }
}
