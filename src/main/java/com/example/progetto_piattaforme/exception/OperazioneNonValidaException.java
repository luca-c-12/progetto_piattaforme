package com.example.progetto_piattaforme.exception;

public class OperazioneNonValidaException extends RuntimeException {

    public OperazioneNonValidaException(String messaggio) {
        super(messaggio);
    }
}
