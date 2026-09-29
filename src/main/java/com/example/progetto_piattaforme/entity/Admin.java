package com.example.progetto_piattaforme.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Utente{

    @Override
    public Ruolo getRuolo() { return Ruolo.ADMIN; }

}
