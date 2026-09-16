package repository;

import entity.CategoriaProdotto;
import entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    List<Prodotto> findAllByIsVisibleTrueAndCategoria(CategoriaProdotto categoria);

    List<Prodotto> findAllByIsVisibleTrueAndNomeContainingIgnoreCase(String nome);

    List<Prodotto> findAllByIsVisibleTrueAndBrandIgnoreCase(String brand);

    List<Prodotto> findAllByIsVisibleTrue();

    List<Prodotto> findAllByIsVisibleTrueAndCategoriaAndBrandIgnoreCase(CategoriaProdotto categoria, String brand);

}

