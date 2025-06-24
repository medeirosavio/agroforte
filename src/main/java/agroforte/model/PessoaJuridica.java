package agroforte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PessoaJuridica extends Pessoa {

    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
}

