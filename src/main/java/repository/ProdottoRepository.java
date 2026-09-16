package repository;

import entity.CategoriaProdotto;
import entity.Prodotto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    List<Prodotto> findAllByIsVisibleTrueAndCategoria(CategoriaProdotto categoria);

    List<Prodotto> findAllByIsVisibleTrueAndNomeContainingIgnoreCase(String nome);

    List<Prodotto> findAllByIsVisibleTrueAndBrandIgnoreCase(String brand);

    List<Prodotto> findAllByIsVisibleTrue();

    List<Prodotto> findAllByIsVisibleTrueAndCategoriaAndBrandIgnoreCase(CategoriaProdotto categoria, String brand);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Prodotto p WHERE p.codiceProdotto = :id")
    Optional<Prodotto> findByIdForUpdate(@Param("id") long id);

}

