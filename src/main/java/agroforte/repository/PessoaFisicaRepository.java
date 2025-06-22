package agroforte.repository;

import agroforte.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
    boolean existsByCpf(String cpf);
    PessoaFisica findByCpf(String cpf);
}

