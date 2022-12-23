package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.dto.UserDto;
import com.cleverdev.clientService.exceptions.UserAlreadyExistException;
import com.cleverdev.clientService.exceptions.UserNotFoundException;
import com.cleverdev.clientService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Vladislav Domaniewski
 */

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Log
@ControllerAdvice
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserDto userDto)
            throws UserAlreadyExistException {
        try {
            userService.addUser(userDto);
            log.info("User was be added!");
        } catch (UserAlreadyExistException e) {
            throw new UserAlreadyExistException("User login busy! Please choose another login!");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-login")
    public String getLoginUser(@RequestParam Long id) {
            return userService.getUser(id).getLogin();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto, @RequestParam Long id)
            throws UserNotFoundException {
        try {
            userService.updateUser(userDto, id);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User was not found! Try else one!");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/delete")
    public void deleteUser(@RequestParam String login) {
        userService.deleteUser(login);
    }
}
