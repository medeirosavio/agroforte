package agroforte.controller;

import agroforte.dto.ParcelaDTO;
import agroforte.service.ParcelaService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParcelaController.class)
public class ParcelaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParcelaService service;

    private ParcelaDTO parcelaDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        parcelaDTO = ParcelaDTO.builder()
                .id(1L)
                .numeroParcela(1)
                .dataVencimento(LocalDate.of(2024, 2, 1))
                .valorParcela(new BigDecimal("1000.0"))
                .operacaoId(1L)
                .build();
    }

    @Test
    void deveListarParcelasPorOperacao() throws Exception {
        when(service.listarPorOperacao(1L)).thenReturn(List.of(parcelaDTO));

        mockMvc.perform(get("/api/parcelas/operacao/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(parcelaDTO.getId()))
                .andExpect(jsonPath("$[0].numeroParcela").value(parcelaDTO.getNumeroParcela()))
                .andExpect(jsonPath("$[0].valorParcela").value(parcelaDTO.getValorParcela()))
                .andExpect(jsonPath("$[0].operacaoId").value(parcelaDTO.getOperacaoId()));

        verify(service, times(1)).listarPorOperacao(1L);
    }

    @Test
    void deveBuscarParcelaPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(parcelaDTO);

        mockMvc.perform(get("/api/parcelas/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(parcelaDTO.getId()))
                .andExpect(jsonPath("$.numeroParcela").value(parcelaDTO.getNumeroParcela()))
                .andExpect(jsonPath("$.valorParcela").value(parcelaDTO.getValorParcela()))
                .andExpect(jsonPath("$.operacaoId").value(parcelaDTO.getOperacaoId()));

        verify(service, times(1)).buscarPorId(1L);
    }
}

