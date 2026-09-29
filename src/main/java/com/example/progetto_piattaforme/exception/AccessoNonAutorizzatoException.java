package com.example.progetto_piattaforme.exception;

public class AccessoNonAutorizzatoException extends RuntimeException {

    public AccessoNonAutorizzatoException(String messaggio) {
        super(messaggio);
    }
}
