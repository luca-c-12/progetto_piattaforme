package com.example.progetto_piattaforme.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.progetto_piattaforme.service.AstaService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChiusuraAsteJob {

    private final AstaService astaService;

    /** Parte un minuto dopo la fine dell'esecuzione precedente. */
    @Scheduled(fixedDelay = 60_000)
    public void chiudiAsteTerminate() {
        for (Long idAsta : astaService.idAsteDaChiudere()) {
            try {
                astaService.chiudiAsta(idAsta);
                log.info("Asta {} chiusa", idAsta);
            } catch (Exception e) {
                // l'asta resta con chiusa = false e verrà ritentata al prossimo giro;
                // intanto le altre vengono chiuse normalmente
                log.error("Chiusura dell'asta {} fallita, verrà ritentata", idAsta, e);
            }
        }
    }
}
