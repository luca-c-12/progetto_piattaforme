package com.example.progetto_piattaforme.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GestoreEccezioni extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RisorsaNonTrovataException.class)
    public ProblemDetail risorsaNonTrovata(RisorsaNonTrovataException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AccessoNonAutorizzatoException.class)
    public ProblemDetail accessoNonAutorizzato(AccessoNonAutorizzatoException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(OperazioneNonValidaException.class)
    public ProblemDetail operazioneNonValida(OperazioneNonValidaException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ProblemDetail conflittoConcorrente(OptimisticLockingFailureException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "I dati sono stati modificati da un'altra operazione. Riprova.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail vincoloViolato(DataIntegrityViolationException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "L'operazione viola un vincolo dei dati (per esempio un duplicato).");
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ProblemDetail ordinamentoNonValido(PropertyReferenceException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Campo di ordinamento non valido: " + e.getPropertyName());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail erroreGenerico(Exception e) {
        log.error("Errore non gestito", e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Errore interno del server.");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ProblemDetail parametriNonValidi(InvalidDataAccessApiUsageException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Parametri di ricerca non validi.");
    }

    @ExceptionHandler(PessimisticLockingFailureException.class)
    public ProblemDetail bloccoConcorrente(PessimisticLockingFailureException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "Operazione in conflitto con un'altra in corso. Riprova.");
    }
}
