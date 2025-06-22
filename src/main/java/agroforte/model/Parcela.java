package agroforte.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroParcela;
    private LocalDate dataVencimento;
    private BigDecimal valorParcela;

    @ManyToOne
    @JoinColumn(name = "operacao_id")
    private Operacao operacao;
}

