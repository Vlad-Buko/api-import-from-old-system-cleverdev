package com.cleverdev.clientService.user.service;

import com.cleverdev.clientService.user.entity.User;
import com.cleverdev.clientService.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by Vladislav Domaniewski
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository user;

    public User addUser(User user1) {
        return user.save(user1);
    }
}
