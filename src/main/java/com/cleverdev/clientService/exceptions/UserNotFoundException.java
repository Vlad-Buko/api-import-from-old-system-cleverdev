package com.cleverdev.clientService.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Vladislav Domaniewski
 */

@Getter
@Setter
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
    }

    private int statusCode;
    private String message;
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
