package com.example.progetto_piattaforme.controller;


import com.example.progetto_piattaforme.dto.ProdottoAdminDTO;
import com.example.progetto_piattaforme.dto.ProdottoDTO;
import com.example.progetto_piattaforme.dto.request.AggiornaProdottoRequest;
import com.example.progetto_piattaforme.dto.request.CreaProdottoRequest;
import com.example.progetto_piattaforme.entity.CategoriaProdotto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.progetto_piattaforme.service.ProdottoService;


@RestController
@RequiredArgsConstructor
public class ProdottoController {

    private final ProdottoService prodottoService;

    @GetMapping("/prodotti")
    public Page<ProdottoDTO> cercaProdotti(@RequestParam(required = false) CategoriaProdotto categoria,
                                           @RequestParam(required = false) String brand,
                                           @RequestParam(required = false) String nome,
                                           @PageableDefault(size = 20, sort = "nome") Pageable paginazione) {
        return prodottoService.cercaProdotti(categoria, brand, nome, paginazione);
    }


    @GetMapping("/prodotti/{codiceProdotto}")
    public ProdottoDTO getProdotto(@PathVariable long codiceProdotto) {
        return prodottoService.getProdotto(codiceProdotto);
    }


    @GetMapping("/admin/prodotti")
    public Page<ProdottoAdminDTO> getProdottiAdmin(
            @PageableDefault(size = 20, sort = "nome") Pageable paginazione) {
        return prodottoService.getProdottiAdmin(paginazione);
    }

    @PostMapping("/admin/prodotti")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdottoAdminDTO creaProdotto(@Valid @RequestBody CreaProdottoRequest richiesta) {
        return prodottoService.creaProdotto(richiesta);
    }

    @PutMapping("/admin/prodotti/{codiceProdotto}")
    public ProdottoAdminDTO aggiornaProdotto(@PathVariable long codiceProdotto,
                                             @Valid @RequestBody AggiornaProdottoRequest richiesta) {
        return prodottoService.aggiornaProdotto(codiceProdotto, richiesta);
    }

    @PatchMapping("/admin/prodotti/{codiceProdotto}/giacenza")
    public ProdottoAdminDTO impostaGiacenza(@PathVariable long codiceProdotto,
                                            @RequestParam @PositiveOrZero int quantita) {
        return prodottoService.impostaGiacenza(codiceProdotto, quantita);
    }

    @PatchMapping("/admin/prodotti/{codiceProdotto}/visibilita")
    public ProdottoAdminDTO impostaVisibilita(@PathVariable long codiceProdotto,
                                              @RequestParam boolean visibile) {
        return prodottoService.impostaVisibilita(codiceProdotto, visibile);
    }
}