package agroforte.service.impl;

import agroforte.dto.OperacaoDTO;
import agroforte.model.Operacao;
import agroforte.repository.OperacaoRepository;
import agroforte.repository.ParcelaRepository;
import agroforte.service.impl.OperacaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperacaoServiceImplTest {

    @InjectMocks
    private OperacaoServiceImpl service;

    @Mock
    private OperacaoRepository operacaoRepository;

    @Mock
    private ParcelaRepository parcelaRepository;

    private Operacao operacao;
    private OperacaoDTO operacaoDTO;

    @BeforeEach
    void setUp() {
        operacao = Operacao.builder()
                .id(1L)
                .dataInicio(LocalDate.of(2025, 6, 1))
                .dataEmissao(LocalDate.of(2025, 6, 1))
                .dataFim(null)
                .quantidadeParcelas(3)
                .dataPrimeiraParcela(LocalDate.of(2025, 7, 1))
                .tempoCarencia(1)
                .valorOperacao(new BigDecimal("3000.00"))
                .taxaMensal(new BigDecimal("1.5"))
                .parcelas(new ArrayList<>())
                .build();

        operacaoDTO = OperacaoDTO.builder()
                .id(1L)
                .dataInicio(LocalDate.of(2025, 6, 1))
                .dataEmissao(LocalDate.of(2025, 6, 1))
                .dataFim(null)
                .quantidadeParcelas(3)
                .dataPrimeiraParcela(LocalDate.of(2025, 7, 1))
                .tempoCarencia(1)
                .valorOperacao(new BigDecimal("3000.00"))
                .taxaMensal(new BigDecimal("1.5"))
                .parcelas(new ArrayList<>())
                .build();
    }

    @Test
    void deveCriarOperacaoComParcelas() {
        // Mocka o save da operação para retornar a operação com id preenchido
        when(operacaoRepository.save(any(Operacao.class))).thenAnswer(invocation -> {
            Operacao op = invocation.getArgument(0);
            op.setId(1L);
            return op;
        });

        // Mocka o saveAll das parcelas para retornar a lista
        when(parcelaRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        OperacaoDTO resultado = service.criar(operacaoDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(3, resultado.getQuantidadeParcelas());

        // Valor da parcela deve ser valor total dividido pela quantidade de parcelas
        BigDecimal valorEsperado = operacaoDTO.getValorOperacao()
                .divide(BigDecimal.valueOf(operacaoDTO.getQuantidadeParcelas()), 2, BigDecimal.ROUND_HALF_UP);

        assertFalse(resultado.getParcelas().isEmpty());
        assertEquals(valorEsperado, resultado.getParcelas().get(0).getValorParcela());

        verify(operacaoRepository, times(1)).save(any(Operacao.class));
        verify(parcelaRepository, times(1)).saveAll(anyList());
    }

    @Test
    void deveBuscarOperacaoPorIdComSucesso() {
        when(operacaoRepository.findById(1L)).thenReturn(Optional.of(operacao));

        OperacaoDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(3, resultado.getQuantidadeParcelas());

        verify(operacaoRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoOperacaoNaoEncontrada() {
        when(operacaoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.buscarPorId(99L));

        assertEquals("Operação não encontrada.", exception.getMessage());

        verify(operacaoRepository, times(1)).findById(99L);
    }

    @Test
    void deveListarTodasOperacoes() {
        when(operacaoRepository.findAll()).thenReturn(List.of(operacao));

        List<OperacaoDTO> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());

        verify(operacaoRepository, times(1)).findAll();
    }

    @Test
    void deveDeletarOperacaoComSucesso() {
        when(operacaoRepository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(operacaoRepository, times(1)).existsById(1L);
        verify(operacaoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarOperacaoInexistente() {
        when(operacaoRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.deletar(99L));

        assertEquals("Operação não encontrada.", exception.getMessage());

        verify(operacaoRepository, times(1)).existsById(99L);
        verify(operacaoRepository, never()).deleteById(anyLong());
    }
}

