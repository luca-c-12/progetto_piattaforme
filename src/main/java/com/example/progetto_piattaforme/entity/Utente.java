package com.example.progetto_piattaforme.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "utenti", uniqueConstraints = {
        @UniqueConstraint(
                name = "unicita_id_keycloak", // Nome del vincolo sul database
                columnNames = {"id_keycloak"} // I nomi delle colonne fisiche sul DB
        )
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ruolo", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente")
    private long idUtente;

    @Column(name = "id_keycloak", nullable = false, updatable = false)
    private String keycloakId;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @CreationTimestamp
    @Column(name = "data_registrazione", nullable = false, updatable = false)
    private LocalDateTime dataRegistrazione;

    @Column(name = "is_visible", nullable = false)
    private boolean isVisible;

    @Version
    private long version;

    public abstract Ruolo getRuolo();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente utente)) return false;
        return utente.idUtente != 0 && idUtente == utente.idUtente;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
