package agroforte.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelaDTO {

    private Long id;

    @NotNull(message = "Número da parcela é obrigatório")
    @Min(value = 1, message = "Número da parcela deve ser ao menos 1")
    private Integer numeroParcela;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDate dataVencimento;

    @NotNull(message = "Valor da parcela é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor da parcela deve ser maior que zero")
    private BigDecimal valorParcela;

    private Long operacaoId;
}

