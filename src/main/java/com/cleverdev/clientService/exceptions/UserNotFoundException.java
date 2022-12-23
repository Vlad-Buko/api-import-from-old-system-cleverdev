package com.cleverdev.clientService.exceptions;

/**
 * Created by Vladislav Domaniewski
 */

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super();
    }
}
