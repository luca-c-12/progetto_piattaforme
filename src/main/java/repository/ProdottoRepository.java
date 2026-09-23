package repository;

import entity.CategoriaProdotto;
import entity.Prodotto;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    @Query("""
        SELECT p FROM Prodotto p
        WHERE p.isVisible = true
          AND p.quantita > 0
          AND (:categoria IS NULL OR p.categoria = :categoria)
          AND (:brand IS NULL OR LOWER(p.brand) = LOWER(:brand))
          AND (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        """)
    Page<Prodotto> cerca(@Param("categoria") CategoriaProdotto categoria,
                         @Param("brand") String brand,
                         @Param("nome") String nome,
                         Pageable paginazione);

}

