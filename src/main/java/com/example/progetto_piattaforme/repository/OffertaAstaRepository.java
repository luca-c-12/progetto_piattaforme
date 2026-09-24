package com.example.progetto_piattaforme.repository;

import com.example.progetto_piattaforme.entity.OffertaAsta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OffertaAstaRepository extends JpaRepository<OffertaAsta, Long> {

    @EntityGraph(attributePaths = "cliente")
    Page<OffertaAsta> findByAsta_IdAstaOrderByImportoOffertaDesc(long idAsta, Pageable paginazione);

    Page<OffertaAsta> findByCliente_IdUtenteOrderByDataOffertaDesc(long idUtente, Pageable paginazione);

    @EntityGraph(attributePaths = "cliente")
    List<OffertaAsta> findByAsta_IdAstaOrderByImportoOffertaDesc(long idAsta);
}
