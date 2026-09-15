package entity;

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
}
