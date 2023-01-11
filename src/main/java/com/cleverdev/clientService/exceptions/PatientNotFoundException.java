package com.cleverdev.clientService.exceptions;

/**
 * Created by Vladislav Domaniewski
 */

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
