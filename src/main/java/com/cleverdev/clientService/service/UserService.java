package com.cleverdev.clientService.service;

import com.cleverdev.clientService.converter.UsersConverter;
import com.cleverdev.clientService.dto.UserDto;
import com.cleverdev.clientService.repository.UserRepository;
import com.cleverdev.clientService.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Vladislav Domaniewski
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository user;
    @Autowired
    private UsersConverter userConverter;

    public User addUser(UserDto userGet) {
        return user.save(userConverter.fromUserDtoToUser(userGet));
    }
}
