package agroforte.service;

import agroforte.dto.PessoaJuridicaDTO;
import java.util.List;

public interface PessoaJuridicaService {

    PessoaJuridicaDTO criar(PessoaJuridicaDTO dto);

    PessoaJuridicaDTO buscarPorId(Long id);

    List<PessoaJuridicaDTO> listarTodos();

    PessoaJuridicaDTO atualizar(Long id, PessoaJuridicaDTO dto);

    void deletar(Long id);
}

