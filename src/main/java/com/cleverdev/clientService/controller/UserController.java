package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.dto.UserDto;
import com.cleverdev.clientService.exceptions.UserAlreadyExistException;
import com.cleverdev.clientService.exceptions.UserNotFoundException;
import com.cleverdev.clientService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Vladislav Domaniewski
 */

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Log4j2
@ControllerAdvice
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto) {
        try {
            userService.addUser(userDto);
            log.info("User was be added!");
        } catch (UserAlreadyExistException e) {
            log.error("User login already to used! Please choose another login!");
            return ResponseEntity.badRequest().body("User login already to used");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-login")
    public ResponseEntity<?> getLoginUser(@RequestParam Long id) {
        String name;
        try {
            name = userService.getUser(id).getLogin();
            log.info("Execute get request");
        } catch (UserNotFoundException ex) {
            log.error("Execute get request, user not found");
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok().body(name);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto,
                                        @RequestParam Long id) {
        try {
            userService.updateUser(userDto, id);
            log.info("User " + userDto.getLogin() + " updated");
        } catch (UserNotFoundException e) {
            log.error("User " + userDto.getLogin() + " not found");
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String login) {
        try {
            userService.deleteUser(login);
            log.info("User " + login + " was be deleted");
        } catch (UserNotFoundException e) {
            log.error("User " + login + " not found");
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok().build();
    }
}
