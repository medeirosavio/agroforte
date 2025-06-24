package agroforte.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInicio;
    private LocalDate dataEmissao;
    private LocalDate dataFim;

    private Integer quantidadeParcelas;
    private LocalDate dataPrimeiraParcela;
    private Integer tempoCarencia; // em meses

    private BigDecimal valorOperacao;
    private BigDecimal taxaMensal;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @OneToMany(mappedBy = "operacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parcela> parcelas;
}

