package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "offerte_aste")
@Getter
@Setter
public class OffertaAsta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_offerta_asta")
    private long idOffertaAsta;

    @Column(name = "importo_offerta", nullable = false, updatable = false)
    private BigDecimal importoOfferta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asta")
    @ToString.Exclude
    private Asta asta;

    @CreationTimestamp
    @Column(name = "data_offerta", nullable = false, updatable = false)
    private LocalDateTime dataOfferta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OffertaAsta)) return false;
        OffertaAsta oa = (OffertaAsta) o;
        return oa.idOffertaAsta != 0 && this.idOffertaAsta == oa.idOffertaAsta;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

