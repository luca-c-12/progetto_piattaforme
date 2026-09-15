package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "utenti")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ruolo", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente")
    private long idUtente;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "hashedPassword", nullable = false)
    private String hashedPassword;

    @CreationTimestamp
    @Column(name = "data_registrazione", nullable = false, updatable = false)
    private LocalDateTime dataRegistrazione;

    @Version
    private long version;

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
