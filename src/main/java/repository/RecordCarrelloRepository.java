package repository;

import entity.RecordCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordCarrelloRepository extends JpaRepository<RecordCarrello,Long> {

    List<RecordCarrello> findByCarrello_IdCarrello(long idCarrello);
}
