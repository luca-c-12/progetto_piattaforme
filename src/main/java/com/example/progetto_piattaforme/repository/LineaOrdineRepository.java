package com.example.progetto_piattaforme.repository;

import com.example.progetto_piattaforme.entity.LineaOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaOrdineRepository extends JpaRepository<LineaOrdine,Long> {

    boolean existsByOrdine_Cliente_IdUtenteAndProdotto_CodiceProdotto(long idUtente, long codiceProdotto);

}
