package agroforte.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFisica extends Pessoa {

    private String cpf;
    private String rg;
    private String estadoCivil;
    private String genero;
    private LocalDate dataNascimento;
    private String nomeMae;
}

