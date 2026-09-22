package dto;

import entity.Cliente;
import entity.OffertaAsta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OffertaAstaDTO(
        long idOffertaAsta,
        long idAsta,
        long idCliente,
        String offerente,
        BigDecimal importo,
        LocalDateTime dataOfferta
) {
    public static OffertaAstaDTO from(OffertaAsta o) {
        Cliente c = o.getCliente();
        return new OffertaAstaDTO(
                o.getIdOffertaAsta(),
                o.getAsta().getIdAsta(),
                c.getIdUtente(),
                nomePubblico(c),
                o.getImportoOfferta(),
                o.getDataOfferta()
        );
    }

    /** "Mario R." — identifica l'offerente senza esporre il cognome completo agli altri utenti. */
    static String nomePubblico(Cliente c) {
        String cognome = c.getCognome();
        return cognome == null || cognome.isBlank()
                ? c.getNome()
                : c.getNome() + " " + cognome.charAt(0) + ".";
    }
}
