package exception;

public class SaldoNonSufficienteException extends OperazioneNonValidaException{

    public SaldoNonSufficienteException(String messaggio){
        super(messaggio);
    }
}
