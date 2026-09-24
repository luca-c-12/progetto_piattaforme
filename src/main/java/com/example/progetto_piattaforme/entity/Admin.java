package com.example.progetto_piattaforme.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Utente{

    @Override
    public Ruolo getRuolo() { return Ruolo.ADMIN; }

}
