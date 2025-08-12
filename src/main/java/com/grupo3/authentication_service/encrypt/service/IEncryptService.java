package com.grupo3.authentication_service.encrypt.service;

public interface IEncryptService {
    String encrypt(String password);
    Boolean compare(String password, String encryptedPassword);
}
