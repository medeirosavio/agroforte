package agroforte.service;

import agroforte.dto.OperacaoDTO;
import java.util.List;

public interface OperacaoService {

    OperacaoDTO criar(OperacaoDTO dto);

    OperacaoDTO buscarPorId(Long id);

    List<OperacaoDTO> listarTodos();

    void deletar(Long id);
}
