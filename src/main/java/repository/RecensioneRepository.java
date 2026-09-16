package repository;

import entity.Recensione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione,Long> {

    List<Recensione> findAllByProdotto_CodiceProdotto(long codiceProdotto);

    List<Recensione> findAllByCliente_IdUtente(long idUtente);
}
