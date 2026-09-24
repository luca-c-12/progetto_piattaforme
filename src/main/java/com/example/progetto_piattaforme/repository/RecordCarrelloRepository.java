package com.example.progetto_piattaforme.repository;

import com.example.progetto_piattaforme.entity.RecordCarrello;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordCarrelloRepository extends JpaRepository<RecordCarrello,Long> {


    @EntityGraph(attributePaths = "prodotto")
    Optional<RecordCarrello> findByCarrello_IdCarrelloAndProdotto_CodiceProdotto(long idCarrello, long codiceProdotto);
}
