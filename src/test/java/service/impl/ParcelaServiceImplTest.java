package service.impl;

import agroforte.dto.ParcelaDTO;
import agroforte.model.Operacao;
import agroforte.model.Parcela;
import agroforte.repository.OperacaoRepository;
import agroforte.repository.ParcelaRepository;
import agroforte.service.impl.ParcelaServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParcelaServiceImplTest {

    @InjectMocks
    private ParcelaServiceImpl service;

    @Mock
    private ParcelaRepository parcelaRepository;

    @Mock
    private OperacaoRepository operacaoRepository;

    private Operacao operacao;
    private Parcela parcela;

    @BeforeEach
    void setUp() {
        operacao = Operacao.builder()
                .id(1L)
                .dataInicio(LocalDate.of(2025, 6, 1))
                .dataEmissao(LocalDate.of(2025, 6, 1))
                .quantidadeParcelas(3)
                .tempoCarencia(1)
                .valorOperacao(new BigDecimal("3000.00"))
                .taxaMensal(new BigDecimal("1.5"))
                .parcelas(new ArrayList<>())
                .build();

        parcela = Parcela.builder()
                .id(10L)
                .operacao(operacao)
                .numeroParcela(1)
                .dataVencimento(LocalDate.of(2025, 7, 1))
                .valorParcela(new BigDecimal("1000.00"))
                .build();

        operacao.setParcelas(List.of(parcela));
    }

    @Test
    void deveListarParcelasPorOperacaoComSucesso() {
        when(operacaoRepository.findById(1L)).thenReturn(Optional.of(operacao));

        List<ParcelaDTO> resultado = service.listarPorOperacao(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getId());
        assertEquals(1, resultado.get(0).getNumeroParcela());
        assertEquals(operacao.getId(), resultado.get(0).getOperacaoId());

        verify(operacaoRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoAoListarParcelasPorOperacaoInexistente() {
        when(operacaoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception =assertThrows(RuntimeException.class,
                () -> service.listarPorOperacao(99L));

        assertEquals("Operação não encontrada.", exception.getMessage());

        verify(operacaoRepository, times(1)).findById(99L);
    }

    @Test
    void deveBuscarParcelaPorIdComSucesso() {
        when(parcelaRepository.findById(10L)).thenReturn(Optional.of(parcela));

        ParcelaDTO resultado = service.buscarPorId(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals(1, resultado.getNumeroParcela());
        assertEquals(operacao.getId(), resultado.getOperacaoId());

        verify(parcelaRepository, times(1)).findById(10L);
    }

    @Test
    void deveLancarExcecaoAoBuscarParcelaPorIdInexistente() {
        when(parcelaRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.buscarPorId(999L));

        assertEquals("Parcela não encontrada.", exception.getMessage());

        verify(parcelaRepository, times(1)).findById(999L);
    }
}

