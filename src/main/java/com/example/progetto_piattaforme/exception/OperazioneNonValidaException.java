package com.example.progetto_piattaforme.exception;

/**
 * Una regola di dominio impedisce l'operazione: scorte insufficienti,
 * offerta non superiore al massimo, asta già chiusa, saldo insufficiente. → 409
 */
public class OperazioneNonValidaException extends RuntimeException {

    public OperazioneNonValidaException(String messaggio) {
        super(messaggio);
    }
}
