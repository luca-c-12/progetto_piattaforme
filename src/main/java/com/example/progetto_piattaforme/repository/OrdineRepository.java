package com.example.progetto_piattaforme.repository;

import com.example.progetto_piattaforme.entity.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Long> {

    Page<Ordine> findByCliente_IdUtenteOrderByDataOrdineDesc(long idUtente, Pageable paginazione);

    @EntityGraph(attributePaths = {"prodottiAcquistati", "prodottiAcquistati.prodotto"})
    Optional<Ordine> findByIdOrdineAndCliente_IdUtente(long idOrdine, long idUtente);

    Page<Ordine> findByDataOrdineBetween(LocalDateTime dal, LocalDateTime al, Pageable paginazione);

    @Override
    @EntityGraph(attributePaths = {"prodottiAcquistati", "prodottiAcquistati.prodotto"})
    Optional<Ordine> findById(Long idOrdine);

}
