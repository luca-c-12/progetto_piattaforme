package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.entity.Prodotto;
import com.example.progetto_piattaforme.exception.QuantitaNonDisponibileException;

public final class ServiceUtils {

    private ServiceUtils() {}

    static void verificaGiacenza(Prodotto prodotto, int richiesta) {
        if (prodotto.getQuantita() < richiesta)
            throw new QuantitaNonDisponibileException("Disponibili solo " + prodotto.getQuantita() + " pezzi di " + prodotto.getNome() + ".");
    }
}
