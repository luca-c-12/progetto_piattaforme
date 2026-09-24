package com.example.progetto_piattaforme.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@DiscriminatorValue("CLIENTE")
@Getter
@Setter
public class Cliente extends Utente{

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrello")
    private Carrello carrello;

    @Column(name = "saldo")
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "indirizzo")
    private String indirizzo;

    @Override
    public Ruolo getRuolo() { return Ruolo.CLIENTE; }




}
