package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.dto.UserDto;
import com.cleverdev.clientService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vladislav Domaniewski
 */

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Log
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public void createUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        log.info("User Added! ---");
    }

    @DeleteMapping("/user/delete")
    public void deleteUser() {
    }
}
