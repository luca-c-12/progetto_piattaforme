package service;

import entity.Prodotto;
import entity.Utente;
import exception.QuantitaNonDisponibileException;
import exception.RisorsaNonTrovataException;

public final class ServiceUtils {

    private ServiceUtils() {}

    static void verificaGiacenza(Prodotto prodotto, int richiesta) {
        if (prodotto.isAvailable(richiesta))
            throw new QuantitaNonDisponibileException("Disponibili solo " + prodotto.getQuantita() + " pezzi di " + prodotto.getNome() + ".");
    }
}
