package com.example.progetto_piattaforme.repository;

import com.example.progetto_piattaforme.entity.Carrello;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello,Long> {

    @EntityGraph(attributePaths = {"prodotti", "prodotti.prodotto"})
    Carrello findByCliente_IdUtente(long idCliente);
}
