package agroforte.repository;

import agroforte.model.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
    List<Parcela> findByOperacaoId(Long operacaoId);
}
