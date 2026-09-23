package service;

import entity.Prodotto;
import exception.QuantitaNonDisponibileException;

public final class ServiceUtils {

    private ServiceUtils() {}

    static void verificaGiacenza(Prodotto prodotto, int richiesta) {
        if (prodotto.isAvailable(richiesta))
            throw new QuantitaNonDisponibileException("Disponibili solo " + prodotto.getQuantita() + " pezzi di " + prodotto.getNome() + ".");
    }
}
