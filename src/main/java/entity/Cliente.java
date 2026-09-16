package entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@DiscriminatorValue("CLIENTE")
@Getter
@Setter
public class Cliente extends Utente{

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrello")
    private Carrello carrello;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "indirizzo")
    private String indirizzo;

    @Column(name = "is_visible")
    private boolean isVisible;


}
