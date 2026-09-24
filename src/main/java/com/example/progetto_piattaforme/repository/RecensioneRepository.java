package com.example.progetto_piattaforme.repository;

import com.example.progetto_piattaforme.entity.Recensione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione,Long> {

    @EntityGraph(attributePaths = "cliente")
    Page<Recensione> findByProdotto_CodiceProdotto(long codiceProdotto, Pageable paginazione);

    Page<Recensione> findByCliente_IdUtente(long idCliente, Pageable paginazione);

    boolean existsByCliente_IdUtenteAndProdotto_CodiceProdotto(long idCliente, long codiceProdotto);

    Optional<Recensione> findByIdRecensioneAndCliente_IdUtente(long idRecensione, long idCliente);
}
