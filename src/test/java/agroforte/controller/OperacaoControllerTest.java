package agroforte.controller;

import agroforte.dto.OperacaoDTO;
import agroforte.dto.ParcelaDTO;
import agroforte.service.OperacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OperacaoController.class)
class OperacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperacaoService service;

    private OperacaoDTO operacaoDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());

        operacaoDTO = OperacaoDTO.builder()
                .id(1L)
                .dataInicio(LocalDate.of(2024, 1, 1))
                .dataEmissao(LocalDate.of(2024, 1, 1))
                .dataFim(LocalDate.of(2025, 1, 1))
                .quantidadeParcelas(12)
                .dataPrimeiraParcela(LocalDate.of(2024, 2, 1))
                .tempoCarencia(1)
                .valorOperacao(new BigDecimal("12000.00"))
                .taxaMensal(new BigDecimal("1.50"))
                .pessoaId(1L)
                .parcelas(List.of(
                        ParcelaDTO.builder()
                                .id(1L)
                                .numeroParcela(1)
                                .dataVencimento(LocalDate.of(2024, 2, 1))
                                .valorParcela(new BigDecimal("1000.00"))
                                .operacaoId(1L)
                                .build()
                ))
                .build();
    }

    @Test
    void deveCriarOperacao() throws Exception {
        when(service.criar(any(OperacaoDTO.class))).thenReturn(operacaoDTO);

        mockMvc.perform(post("/api/operacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operacaoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.quantidadeParcelas").value(12))
                .andExpect(jsonPath("$.valorOperacao").value(12000.00));

        verify(service, times(1)).criar(any(OperacaoDTO.class));
    }

    @Test
    void deveBuscarOperacaoPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(operacaoDTO);

        mockMvc.perform(get("/api/operacoes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.quantidadeParcelas").value(12));

        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void deveListarTodasOperacoes() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(operacaoDTO));

        mockMvc.perform(get("/api/operacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].quantidadeParcelas").value(12));

        verify(service, times(1)).listarTodos();
    }

    @Test
    void deveDeletarOperacao() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/api/operacoes/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deletar(1L);
    }
}

