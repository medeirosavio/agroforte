package agroforte.service.impl;

import agroforte.dto.OperacaoDTO;
import agroforte.dto.ParcelaDTO;
import agroforte.model.Operacao;
import agroforte.model.Parcela;
import agroforte.repository.OperacaoRepository;
import agroforte.repository.ParcelaRepository;
import agroforte.service.OperacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperacaoServiceImpl implements OperacaoService {

    private final OperacaoRepository operacaoRepository;
    private final ParcelaRepository parcelaRepository;

    @Override
    @Transactional
    public OperacaoDTO criar(OperacaoDTO dto) {
        Operacao operacao = toEntity(dto);

        operacao = operacaoRepository.save(operacao);

        List<Parcela> parcelas = gerarParcelas(operacao);
        parcelaRepository.saveAll(parcelas);

        operacao.setParcelas(parcelas);

        return toDTO(operacao);
    }

    @Override
    @Transactional(readOnly = true)
    public OperacaoDTO buscarPorId(Long id) {
        Operacao operacao = operacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operação não encontrada."));
        return toDTO(operacao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperacaoDTO> listarTodos() {
        return operacaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!operacaoRepository.existsById(id)) {
            throw new RuntimeException("Operação não encontrada.");
        }
        operacaoRepository.deleteById(id);
    }


    private List<Parcela> gerarParcelas(Operacao operacao) {
        List<Parcela> parcelas = new ArrayList<>();

        LocalDate dataVencimento = operacao.getDataPrimeiraParcela().plusMonths(operacao.getTempoCarencia());

        BigDecimal valorParcela = operacao.getValorOperacao()
                .divide(BigDecimal.valueOf(operacao.getQuantidadeParcelas()), 2, BigDecimal.ROUND_HALF_UP);

        for (int i = 1; i <= operacao.getQuantidadeParcelas(); i++) {
            Parcela parcela = Parcela.builder()
                    .operacao(operacao)
                    .numeroParcela(i)
                    .dataVencimento(dataVencimento)
                    .valorParcela(valorParcela)
                    .build();

            parcelas.add(parcela);

            // Incrementa para o próximo mês
            dataVencimento = dataVencimento.plusMonths(1);
        }

        return parcelas;
    }


    private OperacaoDTO toDTO(Operacao entity) {
        return OperacaoDTO.builder()
                .id(entity.getId())
                .dataInicio(entity.getDataInicio())
                .dataEmissao(entity.getDataEmissao())
                .dataFim(entity.getDataFim())
                .quantidadeParcelas(entity.getQuantidadeParcelas())
                .dataPrimeiraParcela(entity.getDataPrimeiraParcela())
                .tempoCarencia(entity.getTempoCarencia())
                .valorOperacao(entity.getValorOperacao())
                .taxaMensal(entity.getTaxaMensal())
                .parcelas(entity.getParcelas() != null ? entity.getParcelas()
                        .stream()
                        .map(p -> ParcelaDTO.builder()
                                .id(p.getId())
                                .numeroParcela(p.getNumeroParcela())
                                .dataVencimento(p.getDataVencimento())
                                .valorParcela(p.getValorParcela())
                                .build())
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    private Operacao toEntity(OperacaoDTO dto) {
        return Operacao.builder()
                .dataInicio(dto.getDataInicio())
                .dataEmissao(dto.getDataEmissao())
                .dataFim(dto.getDataFim())
                .quantidadeParcelas(dto.getQuantidadeParcelas())
                .dataPrimeiraParcela(dto.getDataPrimeiraParcela())
                .tempoCarencia(dto.getTempoCarencia())
                .valorOperacao(dto.getValorOperacao())
                .taxaMensal(dto.getTaxaMensal())
                .build();
    }
}

