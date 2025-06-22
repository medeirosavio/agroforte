package agroforte.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaFisicaDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres")
    private String cpf;

    private String rg;

    private String estadoCivil;

    private String genero;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    private String nomeMae;

    private String celular;

    private String nacionalidade;
}

