package agroforte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PessoaFisica extends Pessoa {

    private String cpf;
    private String rg;
    private String estadoCivil;
    private String genero;
    private LocalDate dataNascimento;
    private String nomeMae;
}


