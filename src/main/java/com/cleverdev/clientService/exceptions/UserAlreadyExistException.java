package com.cleverdev.clientService.exceptions;

/**
 * Created by Vladislav Domaniewski
 */

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
