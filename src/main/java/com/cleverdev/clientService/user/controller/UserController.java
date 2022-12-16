package com.cleverdev.clientService.user.controller;

import com.cleverdev.clientService.user.dto.UserDto;
import com.cleverdev.clientService.user.entity.User;
import com.cleverdev.clientService.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vladislav Domaniewski
 */

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity.BodyBuilder createUser(@RequestBody User userDto) {

        userService.addUser(userDto);
        return ResponseEntity.ok();
    }
}
