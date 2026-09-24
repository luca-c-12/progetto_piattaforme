package com.example.progetto_piattaforme.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "prodotti")
@Getter
@Setter
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codice_prodotto")
    private long codiceProdotto;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "prezzo", nullable = false)
    private BigDecimal prezzo;

    @Column(name = "descrizione", length = 2000)
    private String descrizione;

    @Column(name = "brand")
    private String brand;

    @Column(name = "quantita", nullable = false)
    private int quantita;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaProdotto categoria;

    @Column(name = "url_immagine", length = 1000)
    private String urlImmagine;


    @Column(name = "is_visible", nullable = false)
    private boolean isVisible;


    public boolean isInCatalogo() {
        return isVisible && quantita > 0;
    }

    @Version
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prodotto)) return false;
        Prodotto p = (Prodotto) o;
        return codiceProdotto != 0 && codiceProdotto == p.codiceProdotto;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}