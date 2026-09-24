package com.example.progetto_piattaforme.entity;

import jakarta.persistence.EnumeratedValue;

public enum VotoRecensione {
    UNO(1), DUE(2), TRE(3), QUATTRO(4), CINQUE(5);


    @EnumeratedValue
    private final int value;

    private VotoRecensione(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VotoRecensione daValore(int valore) {
        for (VotoRecensione v : values())
            if (v.value == valore) return v;
        throw new IllegalArgumentException("Voto non valido: " + valore);
    }
}
