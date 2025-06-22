package agroforte.repository;

import agroforte.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
    boolean existsByCnpj(String cnpj);
    PessoaJuridica findByCnpj(String cnpj);
}

