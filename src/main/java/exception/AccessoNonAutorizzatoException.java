package exception;

/** La risorsa esiste ma non appartiene a chi la richiede. → 403 */
public class AccessoNonAutorizzatoException extends RuntimeException {

    public AccessoNonAutorizzatoException(String messaggio) {
        super(messaggio);
    }
}
