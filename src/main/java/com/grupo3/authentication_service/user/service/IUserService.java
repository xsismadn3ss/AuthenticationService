package com.grupo3.authentication_service.user.service;

import com.grupo3.authentication_service.user.dto.UserDto;

public interface IUserService {
    UserDto findByUsername(String username);
    UserDto findById(Long id);
}
