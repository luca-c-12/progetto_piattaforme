package exception;

/** La risorsa richiesta non esiste. → 404 */
public class RisorsaNonTrovataException extends RuntimeException {

    public RisorsaNonTrovataException(String messaggio) {
        super(messaggio);
    }
}
