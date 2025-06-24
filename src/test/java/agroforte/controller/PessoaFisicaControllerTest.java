package agroforte.controller;

import agroforte.dto.PessoaFisicaDTO;
import agroforte.service.PessoaFisicaService;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PessoaFisicaController.class)
class PessoaFisicaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaFisicaService service;

    private PessoaFisicaDTO pessoaFisicaDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());

        pessoaFisicaDTO = PessoaFisicaDTO.builder()
                .id(1L)
                .nome("João Silva")
                .cpf("12345678900")
                .email("joao@email.com")
                .rg("1234567")
                .celular("11999999999")
                .estadoCivil("Solteiro")
                .genero("Masculino")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .nomeMae("Maria Silva")
                .nacionalidade("Brasileiro")
                .build();
    }

    @Test
    void deveCriarPessoaFisica() throws Exception {
        when(service.criar(any(PessoaFisicaDTO.class))).thenReturn(pessoaFisicaDTO);

        mockMvc.perform(post("/api/pessoas-fisicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaFisicaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678900"));

        verify(service, times(1)).criar(any(PessoaFisicaDTO.class));
    }

    @Test
    void deveBuscarPessoaFisicaPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(pessoaFisicaDTO);

        mockMvc.perform(get("/api/pessoas-fisicas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678900"));

        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void deveListarTodasPessoasFisicas() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(pessoaFisicaDTO));

        mockMvc.perform(get("/api/pessoas-fisicas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("João Silva"));

        verify(service, times(1)).listarTodos();
    }

    @Test
    void deveAtualizarPessoaFisica() throws Exception {
        when(service.atualizar(eq(1L), any(PessoaFisicaDTO.class))).thenReturn(pessoaFisicaDTO);

        mockMvc.perform(put("/api/pessoas-fisicas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaFisicaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(service, times(1)).atualizar(eq(1L), any(PessoaFisicaDTO.class));
    }

    @Test
    void deveDeletarPessoaFisica() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/api/pessoas-fisicas/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deletar(1L);
    }
}


