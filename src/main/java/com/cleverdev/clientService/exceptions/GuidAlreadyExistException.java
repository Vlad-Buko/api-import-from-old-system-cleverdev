package com.cleverdev.clientService.exceptions;

/**
 * Created by Vladislav Domaniewski
 */

public class GuidAlreadyExistException extends Exception{
    public GuidAlreadyExistException(String message) {
        super(message);
    }
}
