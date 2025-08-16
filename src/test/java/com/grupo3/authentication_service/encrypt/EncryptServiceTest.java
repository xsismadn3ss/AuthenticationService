package com.grupo3.authentication_service.encrypt;

import com.grupo3.authentication_service.encrypt.service.impl.EncryptServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptServiceTest {
    @Test
    public void testEncrypt(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        EncryptServiceImpl encryptService = new EncryptServiceImpl(passwordEncoder);

        String password = "1234";
        String encryptedPassword = encryptService.encrypt(password);
        System.out.println(encryptedPassword);
        assert encryptedPassword != null;
        assert !encryptedPassword.isEmpty();
    }
}
