package repository;

import entity.OffertaAsta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffertaAstaRepository extends JpaRepository<OffertaAsta, Long> {

    List<OffertaAsta> findAllByAsta_IdAsta(long idAsta);

    List<OffertaAsta> findAllByCliente_IdUtente(long idUtente);
}
