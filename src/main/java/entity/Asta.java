package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "aste")
@Getter
@Setter
public class Asta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asta")
    private long idAsta;

    @OneToOne
    @JoinColumn(name = "id_prodotto", nullable = false)
    private Prodotto prodotto;

    @Column(name = "inizio_asta")
    @CreationTimestamp
    private LocalDateTime dataInizio;

    @Column(name = "fine_asta", nullable = false)
    private LocalDateTime dataFine;

    @Column(name = "prezzo_base", nullable = false, updatable = false)
    private BigDecimal prezzoBase;

    @OneToMany(mappedBy = "asta", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<OffertaAsta> offerteRicevute = new ArrayList<>();

   // @ManyToOne(fetch = FetchType.LAZY) //sarebbe semanticamente un one to one, ma si fa many to one per fetch lazy
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offerta_max")
    private OffertaAsta offertaMax;

    @Column(name = "is_visible")
    private boolean isVisible;

    @Version
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asta)) return false;
        Asta a = (Asta) o;
        return a.idAsta != 0 && this.idAsta == a.idAsta;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
