package agroforte.service.impl;

import agroforte.dto.ParcelaDTO;
import agroforte.model.Operacao;
import agroforte.model.Parcela;
import agroforte.repository.OperacaoRepository;
import agroforte.repository.ParcelaRepository;
import agroforte.service.ParcelaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParcelaServiceImpl implements ParcelaService {

    private final ParcelaRepository parcelaRepository;
    private final OperacaoRepository operacaoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ParcelaDTO> listarPorOperacao(Long operacaoId) {
        Operacao operacao = operacaoRepository.findById(operacaoId)
                .orElseThrow(() -> new RuntimeException("Operação não encontrada."));

        return operacao.getParcelas().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ParcelaDTO buscarPorId(Long id) {
        Parcela parcela = parcelaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parcela não encontrada."));
        return toDTO(parcela);
    }


    private ParcelaDTO toDTO(Parcela entity) {
        return ParcelaDTO.builder()
                .id(entity.getId())
                .numeroParcela(entity.getNumeroParcela())
                .dataVencimento(entity.getDataVencimento())
                .valorParcela(entity.getValorParcela())
                .operacaoId(entity.getOperacao().getId())
                .build();
    }
}

