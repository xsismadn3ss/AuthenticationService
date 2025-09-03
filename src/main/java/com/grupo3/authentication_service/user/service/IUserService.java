package com.grupo3.authentication_service.user.service;

import shareddtos.usersmodule.auth.UserDto;

public interface IUserService {
    UserDto findByUsername(String username);
    UserDto findById(Long id);
}
