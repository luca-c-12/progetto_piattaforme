package repository;

import entity.Cliente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    @EntityGraph(attributePaths = {"carrello.prodotti", "carrello.prodotti.prodotto"})
    Optional<Cliente> findConCarrelloByIdUtente(long idUtente);

}
