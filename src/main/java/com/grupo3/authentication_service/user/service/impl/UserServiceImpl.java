package com.grupo3.authentication_service.user.service.impl;

import com.grupo3.authentication_service.user.dto.UserDto;
import com.grupo3.authentication_service.user.entity.User;
import com.grupo3.authentication_service.user.repository.UserRepository;
import com.grupo3.authentication_service.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setDui(user.getDui());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        return getUserDto(user);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return getUserDto(user);
    }
}
