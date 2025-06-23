package agroforte.service;

import agroforte.dto.ParcelaDTO;
import java.util.List;

public interface ParcelaService {

    List<ParcelaDTO> listarPorOperacao(Long operacaoId);

    ParcelaDTO buscarPorId(Long id);
}
