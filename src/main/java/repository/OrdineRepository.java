package repository;

import entity.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Long> {

    List<Ordine> findByClienteIdUtente(long idUtente);

    @Query("SELECT o FROM Ordine o JOIN FETCH o.prodottiAcquistati WHERE o.cliente.idUtente = :id")
    List<Ordine> findConRigheByCliente(@Param("id") long id);

}
