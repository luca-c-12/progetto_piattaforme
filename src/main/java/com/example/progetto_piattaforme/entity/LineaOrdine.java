package com.example.progetto_piattaforme.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "prodotti_acquistati")
@Getter
@Setter
public class LineaOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linea_ordine")
    private long idLineaOrdine;

    @Column(name = "quantita")
    private int quantita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prodotto")
    private Prodotto prodotto;

    @Column(name = "nome_prodotto")
    private String nomeProdotto;

    @Column(name = "prezzo_unitario")
    private BigDecimal prezzoUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ordine", nullable = false)
    @ToString.Exclude
    private Ordine ordine;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineaOrdine that)) return false;
        return that.idLineaOrdine != 0 && idLineaOrdine == that.idLineaOrdine;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
