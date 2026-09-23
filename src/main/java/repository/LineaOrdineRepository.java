package repository;

import entity.LineaOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineaOrdineRepository extends JpaRepository<LineaOrdine,Long> {

    boolean existsByOrdine_Cliente_IdUtenteAndProdotto_CodiceProdotto(long idUtente, long codiceProdotto);

}
