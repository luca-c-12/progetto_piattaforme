package com.example.progetto_piattaforme.repository;

import com.example.progetto_piattaforme.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente,Long> {

    boolean existsByEmail(String email);

    Optional<Utente> findByEmail(String email);

    boolean existsByIdUtenteAndIsVisibleTrue(long idUtente);
}
