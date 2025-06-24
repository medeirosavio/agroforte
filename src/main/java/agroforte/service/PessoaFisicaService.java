package agroforte.service;

import agroforte.dto.PessoaFisicaDTO;
import java.util.List;

public interface PessoaFisicaService {
    PessoaFisicaDTO criar(PessoaFisicaDTO dto);
    PessoaFisicaDTO buscarPorId(Long id);
    List<PessoaFisicaDTO> listarTodos();
    PessoaFisicaDTO atualizar(Long id, PessoaFisicaDTO dto);
    void deletar(Long id);
}

