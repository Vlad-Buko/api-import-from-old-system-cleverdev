package com.cleverdev.clientService.exceptions;

/**
 * Created by Vladislav Domaniewski
 */

public class NoteAlreadyExistException extends Exception {
    public NoteAlreadyExistException(String message) {
        super(message);
    }
}
