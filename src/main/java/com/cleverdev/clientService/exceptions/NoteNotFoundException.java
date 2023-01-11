package com.cleverdev.clientService.exceptions;

/**
 * Created by Vladislav Domaniewski
 */

public class NoteNotFoundException extends Exception{
    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException() {
    }
}
