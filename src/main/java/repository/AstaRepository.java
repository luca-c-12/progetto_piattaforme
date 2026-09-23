package repository;

import entity.Asta;
import entity.CategoriaProdotto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AstaRepository extends JpaRepository<Asta,Long> {


    @EntityGraph(attributePaths = {"prodotto", "offertaMax", "offertaMax.cliente"})
    @Query("""
            SELECT a FROM Asta a
            WHERE a.isVisible = true
              AND a.dataInizio <= :adesso
              AND a.dataFine > :adesso
              AND (:categoria IS NULL OR a.prodotto.categoria = :categoria)
              AND (:nome IS NULL OR LOWER(a.prodotto.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
            """)
    Page<Asta> cercaAsteInCorso(@Param("adesso") LocalDateTime adesso,
                                @Param("categoria") CategoriaProdotto categoria,
                                @Param("nome") String nome,
                                Pageable paginazione);


    @EntityGraph(attributePaths = {"prodotto", "offertaMax", "offertaMax.cliente"})
    Page<Asta> findByIsVisibleTrueAndDataInizioAfter(LocalDateTime adesso, Pageable paginazione);


    @EntityGraph(attributePaths = {"prodotto", "offertaMax", "offertaMax.cliente"})
    Page<Asta> findByDataFineLessThanEqual(LocalDateTime adesso, Pageable paginazione);

    @Override
    @EntityGraph(attributePaths = {"prodotto", "offertaMax", "offertaMax.cliente"})
    Optional<Asta> findById(Long idAsta);

    boolean existsByProdotto_CodiceProdottoAndIsVisibleTrueAndDataFineAfter(long codiceProdotto, LocalDateTime adesso);

    @Query("SELECT a.idAsta FROM Asta a WHERE a.chiusa = false AND a.dataFine <= :adesso")
    List<Long> findIdDaChiudere(@Param("adesso") LocalDateTime adesso);

    boolean existsByProdotto_CodiceProdottoAndChiusaFalse(long codiceProdotto);
}
