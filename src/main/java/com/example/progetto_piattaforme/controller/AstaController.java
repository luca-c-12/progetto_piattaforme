package com.example.progetto_piattaforme.controller;


import com.example.progetto_piattaforme.dto.AstaDTO;
import com.example.progetto_piattaforme.dto.OffertaAstaDTO;
import com.example.progetto_piattaforme.dto.request.CreaAstaRequest;
import com.example.progetto_piattaforme.dto.request.OffertaRequest;
import com.example.progetto_piattaforme.entity.CategoriaProdotto;
import com.example.progetto_piattaforme.security.UtenteCorrente;
import com.example.progetto_piattaforme.service.AstaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AstaController {

    private final AstaService astaService;
    private final UtenteCorrente utenteCorrente;

    @GetMapping("/aste")
    public Page<AstaDTO> cercaAsteInCorso(@RequestParam(required = false) CategoriaProdotto categoria,
                                          @RequestParam(required = false) String nome,
                                          @PageableDefault(size = 20, sort = "dataFine") Pageable paginazione) {
        return astaService.cercaAsteInCorso(categoria, nome, paginazione);
    }

    @GetMapping("/aste/programmate")
    public Page<AstaDTO> getAsteProgrammate(@PageableDefault(size = 20, sort = "dataInizio") Pageable paginazione) {
        return astaService.getAsteProgrammate(paginazione);
    }

    @GetMapping("/aste/mie-offerte")
    @PreAuthorize("hasRole('CLIENTE')")
    public Page<OffertaAstaDTO> getOfferteCliente(@PageableDefault(size = 20) Pageable paginazione) {
        return astaService.getOfferteCliente(utenteCorrente.id(), paginazione);
    }

    @GetMapping("/aste/{idAsta}")
    public AstaDTO getAsta(@PathVariable long idAsta) {
        return astaService.getAsta(idAsta);
    }

    @GetMapping("/aste/{idAsta}/offerte")
    public Page<OffertaAstaDTO> getOfferte(@PathVariable long idAsta,
                                           @PageableDefault(size = 20) Pageable paginazione) {
        return astaService.getOfferte(idAsta, paginazione);
    }

    @PostMapping("/aste/{idAsta}/offerte")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CLIENTE')")
    public AstaDTO faiOfferta(@PathVariable long idAsta, @Valid @RequestBody OffertaRequest richiesta) {
        return astaService.faiOfferta(utenteCorrente.id(), idAsta, richiesta);
    }

    @PostMapping("/admin/aste")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public AstaDTO creaAsta(@Valid @RequestBody CreaAstaRequest richiesta) {
        return astaService.creaAsta(richiesta);
    }

    @DeleteMapping("/admin/aste/{idAsta}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void annullaAsta(@PathVariable long idAsta) {
        astaService.annullaAsta(idAsta);
    }
}
