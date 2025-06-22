package agroforte.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaJuridica extends Pessoa {

    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
}

