package com.example.progetto_piattaforme.component;

import com.example.progetto_piattaforme.service.AstaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChiusuraAsteJob {

    private final AstaService astaService;

    @Scheduled(fixedDelay = 60_000)
    public void chiudiAsteTerminate() {
        for (Long idAsta : astaService.idAsteDaChiudere()) {
            try {
                astaService.chiudiAsta(idAsta);
                log.info("Asta {} chiusa", idAsta);
            } catch (Exception e) {
                log.error("Chiusura dell'asta {} fallita, verrà ritentata", idAsta, e);
            }
        }
    }
}
