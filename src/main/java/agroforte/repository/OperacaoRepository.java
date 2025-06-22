package agroforte.repository;

import agroforte.model.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao, Long> {
    List<Operacao> findByPessoaId(Long pessoaId);
}

