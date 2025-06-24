package agroforte.controller;

import agroforte.dto.PessoaJuridicaDTO;
import agroforte.service.PessoaJuridicaService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PessoaJuridicaController.class)
class PessoaJuridicaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaJuridicaService service;

    private PessoaJuridicaDTO pessoaJuridicaDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());

        pessoaJuridicaDTO = PessoaJuridicaDTO.builder()
                .id(1L)
                .razaoSocial("Empresa XYZ Ltda")
                .nome("XYZ")
                .cnpj("12345678000199")
                .email("contato@xyz.com")
                .inscricaoEstadual("ISENTO")
                .nacionalidade("brasileiro")
                .inscricaoMunicipal("ISENTO")
                .celular("11999999999")
                .build();
    }

    @Test
    void deveCriarPessoaJuridica() throws Exception {
        when(service.criar(any(PessoaJuridicaDTO.class))).thenReturn(pessoaJuridicaDTO);

        mockMvc.perform(post("/api/pessoas-juridicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaJuridicaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.razaoSocial").value("Empresa XYZ Ltda"))
                .andExpect(jsonPath("$.cnpj").value("12345678000199"));

        verify(service, times(1)).criar(any(PessoaJuridicaDTO.class));
    }

    @Test
    void deveBuscarPessoaJuridicaPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(pessoaJuridicaDTO);

        mockMvc.perform(get("/api/pessoas-juridicas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.razaoSocial").value("Empresa XYZ Ltda"))
                .andExpect(jsonPath("$.cnpj").value("12345678000199"));

        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void deveListarTodasPessoasJuridicas() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(pessoaJuridicaDTO));

        mockMvc.perform(get("/api/pessoas-juridicas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].razaoSocial").value("Empresa XYZ Ltda"));

        verify(service, times(1)).listarTodos();
    }

    @Test
    void deveAtualizarPessoaJuridica() throws Exception {
        when(service.atualizar(eq(1L), any(PessoaJuridicaDTO.class))).thenReturn(pessoaJuridicaDTO);

        mockMvc.perform(put("/api/pessoas-juridicas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaJuridicaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.razaoSocial").value("Empresa XYZ Ltda"));

        verify(service, times(1)).atualizar(eq(1L), any(PessoaJuridicaDTO.class));
    }

    @Test
    void deveDeletarPessoaJuridica() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/api/pessoas-juridicas/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deletar(1L);
    }
}

