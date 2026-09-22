package repository;

import entity.Prodotto;
import entity.RecordCarrello;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordCarrelloRepository extends JpaRepository<RecordCarrello,Long> {

    List<RecordCarrello> findByCarrello_IdCarrello(long idCarrello);

    @EntityGraph(attributePaths = "prodotto")
    Optional<RecordCarrello> findByCarrello_IdCarrelloAndProdotto_CodiceProdotto(long idCarrello, long codiceProdotto);
}
