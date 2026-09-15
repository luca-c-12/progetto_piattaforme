package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ordini")
@Getter
@Setter
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordine")
    private long idOrdine;

    @Column(name = "data_ordine", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataOrdine;

    @Column(name = "subtotale", updatable = false)
    private BigDecimal totalePagato;

    @ManyToOne
    @JoinColumn(name = "id_cliente", updatable = false)
    private Cliente cliente;

    @Column(name = "indirizzo_spedizione", updatable = false)
    private String indirizzoSpedizione;


    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaOrdine> prodottiAcquistati = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ordine ordine)) return false;
        return ordine.idOrdine != 0 && this.idOrdine == ordine.idOrdine;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
