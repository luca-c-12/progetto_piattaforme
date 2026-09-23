package service;

import dto.ProdottoAdminDTO;
import dto.ProdottoDTO;
import dto.request.AggiornaProdottoRequest;
import dto.request.CreaProdottoRequest;
import entity.CategoriaProdotto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdottoService {

    Page<ProdottoDTO> cercaProdotti(CategoriaProdotto categoria, String brand, String nome,
                                    Pageable paginazione);

    ProdottoDTO getProdotto(long codiceProdotto);


    Page<ProdottoAdminDTO> getProdottiAdmin(Pageable paginazione);

    ProdottoAdminDTO creaProdotto(CreaProdottoRequest richiesta);


    ProdottoAdminDTO aggiornaProdotto(long codiceProdotto, AggiornaProdottoRequest richiesta);

    ProdottoAdminDTO impostaGiacenza(long codiceProdotto, int quantita);

    ProdottoAdminDTO impostaVisibilita(long codiceProdotto, boolean visibile);
}
