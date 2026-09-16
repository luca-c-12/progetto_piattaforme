package repository;

import entity.RecordCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordCarrelloRepository extends JpaRepository<RecordCarrello,Long> {

    Optional<RecordCarrello> findByCarrello_IdCarrello(long idCarrello);
}
