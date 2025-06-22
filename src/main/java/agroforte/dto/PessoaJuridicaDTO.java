package agroforte.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaJuridicaDTO {

    private Long id;

    @NotBlank(message = "Razão social é obrigatória")
    private String razaoSocial;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "CNPJ é obrigatório")
    @Size(min = 14, max = 18, message = "CNPJ deve ter entre 14 e 18 caracteres")
    private String cnpj;

    private String inscricaoEstadual;

    private String inscricaoMunicipal;

    private String celular;

    private String nacionalidade;
}

