package repository;

import entity.Asta;
import entity.CategoriaProdotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AstaRepository extends JpaRepository<Asta,Long> {

    List<Asta> findAllByIsVisibleTrueAndDataInizioBeforeAndDataFineAfter(LocalDateTime adessoInizio, LocalDateTime adessoFine);

    List<Asta> findAllByDataInizioGreaterThanEqual(LocalDateTime data);

    List<Asta> findAllByDataFineLessThanEqual(LocalDateTime adesso);

    List<Asta> findAllByIsVisibleTrueAndProdottoCategoria(CategoriaProdotto categoria);

    List<Asta> findAllByIsVisibleTrueAndProdotto_NomeContainingIgnoreCase(String nomeProdotto)
}
