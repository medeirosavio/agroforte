package agroforte.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperacaoDTO {

    private Long id;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "Data de emissão é obrigatória")
    private LocalDate dataEmissao;

    private LocalDate dataFim;

    @NotNull(message = "Quantidade de parcelas é obrigatória")
    @Min(value = 1, message = "Quantidade de parcelas deve ser ao menos 1")
    private Integer quantidadeParcelas;

    @NotNull(message = "Data da primeira parcela é obrigatória")
    private LocalDate dataPrimeiraParcela;

    @NotNull(message = "Tempo de carência é obrigatório")
    @Min(value = 0, message = "Tempo de carência não pode ser negativo")
    private Integer tempoCarencia; // em meses

    @NotNull(message = "Valor da operação é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor da operação deve ser maior que zero")
    private BigDecimal valorOperacao;

    @NotNull(message = "Taxa mensal é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true, message = "Taxa mensal deve ser zero ou positiva")
    private BigDecimal taxaMensal;

    @NotNull(message = "ID da pessoa é obrigatório")
    private Long pessoaId;

    private List<ParcelaDTO> parcelas;
}
