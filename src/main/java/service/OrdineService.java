package service;

import dto.OrdineDTO;
import dto.OrdineSintesiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OrdineService {


    OrdineDTO effettuaCheckout(long idCliente);

    OrdineDTO getOrdine(long idCliente, long idOrdine);

    Page<OrdineSintesiDTO> getOrdiniCliente(long idCliente, Pageable paginazione);


    Page<OrdineSintesiDTO> getTuttiGliOrdini(LocalDate dal, LocalDate al, Pageable paginazione);

    OrdineDTO getOrdineComeAdmin(long idOrdine);

}
