package com.example.progetto_piattaforme.exception;

public class SaldoNonSufficienteException extends OperazioneNonValidaException{

    public SaldoNonSufficienteException(String messaggio){
        super(messaggio);
    }
}
