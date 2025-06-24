package agroforte.service.impl;

import agroforte.dto.PessoaJuridicaDTO;
import agroforte.model.PessoaJuridica;
import agroforte.repository.PessoaJuridicaRepository;
import agroforte.service.impl.PessoaJuridicaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaJuridicaServiceImplTest {

    @InjectMocks
    private PessoaJuridicaServiceImpl service;

    @Mock
    private PessoaJuridicaRepository repository;

    private PessoaJuridica pessoaJuridica;
    private PessoaJuridicaDTO pessoaJuridicaDTO;

    @BeforeEach
    void setUp() {
        pessoaJuridica = PessoaJuridica.builder()
                .id(1L)
                .razaoSocial("Empresa XYZ Ltda")
                .email("contato@xyz.com")
                .cnpj("12345678000199")
                .inscricaoEstadual("123456789")
                .inscricaoMunicipal("987654321")
                .celular("11999999999")
                .nacionalidade("Brasileira")
                .build();

        pessoaJuridicaDTO = PessoaJuridicaDTO.builder()
                .id(1L)
                .razaoSocial("Empresa XYZ Ltda")
                .email("contato@xyz.com")
                .cnpj("12345678000199")
                .inscricaoEstadual("123456789")
                .inscricaoMunicipal("987654321")
                .celular("11999999999")
                .nacionalidade("Brasileira")
                .build();
    }

    @Test
    void deveCriarPessoaJuridicaComSucesso() {
        when(repository.existsByCnpj(pessoaJuridicaDTO.getCnpj())).thenReturn(false);
        when(repository.save(any(PessoaJuridica.class))).thenReturn(pessoaJuridica);

        PessoaJuridicaDTO resultado = service.criar(pessoaJuridicaDTO);

        assertNotNull(resultado);
        assertEquals(pessoaJuridicaDTO.getRazaoSocial(), resultado.getRazaoSocial());
        assertEquals(pessoaJuridicaDTO.getCnpj(), resultado.getCnpj());

        verify(repository, times(1)).existsByCnpj(pessoaJuridicaDTO.getCnpj());
        verify(repository, times(1)).save(any(PessoaJuridica.class));
    }

    @Test
    void deveLancarExcecaoAoCriarCnpjDuplicado() {
        when(repository.existsByCnpj(pessoaJuridicaDTO.getCnpj())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.criar(pessoaJuridicaDTO));

        assertEquals("CNPJ já cadastrado.", exception.getMessage());

        verify(repository, times(1)).existsByCnpj(pessoaJuridicaDTO.getCnpj());
        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarPessoaJuridicaPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaJuridica));

        PessoaJuridicaDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Empresa XYZ Ltda", resultado.getRazaoSocial());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoPessoaJuridicaNaoEncontrada() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.buscarPorId(99L));

        assertEquals("Pessoa jurídica não encontrada.", exception.getMessage());

        verify(repository, times(1)).findById(99L);
    }

    @Test
    void deveListarTodasPessoasJuridicas() {
        when(repository.findAll()).thenReturn(List.of(pessoaJuridica));

        List<PessoaJuridicaDTO> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Empresa XYZ Ltda", resultado.get(0).getRazaoSocial());

        verify(repository, times(1)).findAll();
    }

    @Test
    void deveAtualizarPessoaJuridicaComSucesso() {
        Long id = 1L;
        String cnpj = "12345678000199";

        PessoaJuridicaDTO dto = PessoaJuridicaDTO.builder()
                .razaoSocial("Empresa XYZ Ltda")
                .cnpj(cnpj) // Mesmo CNPJ
                .build();

        PessoaJuridica pessoaOriginal = PessoaJuridica.builder()
                .id(id)
                .cnpj(cnpj) // Mesmo CNPJ
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(pessoaOriginal));
        when(repository.save(any(PessoaJuridica.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PessoaJuridicaDTO atualizado = service.atualizar(id, dto);

        assertNotNull(atualizado);
        assertEquals(dto.getRazaoSocial(), atualizado.getRazaoSocial());

        verify(repository, times(1)).findById(id);
        verify(repository, never()).existsByCnpj(anyString()); // Não deve chamar porque CNPJ não mudou
        verify(repository, times(1)).save(any(PessoaJuridica.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarComCnpjDuplicado() {
        String cnpjOriginal = "12345678000199";
        String cnpjNovo = "98765432000188";

        PessoaJuridicaDTO dtoComCnpjDuplicado = PessoaJuridicaDTO.builder()
                .razaoSocial("Empresa XYZ Ltda")
                .cnpj(cnpjNovo) // CNPJ diferente do original
                .build();

        PessoaJuridica pessoaOriginal = PessoaJuridica.builder()
                .id(1L)
                .cnpj(cnpjOriginal)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(pessoaOriginal));
        when(repository.existsByCnpj(cnpjNovo)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.atualizar(1L, dtoComCnpjDuplicado));

        assertEquals("CNPJ já cadastrado para outra empresa.", exception.getMessage());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).existsByCnpj(cnpjNovo);
        verify(repository, never()).save(any());
    }

    @Test
    void deveDeletarPessoaJuridicaComSucesso() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarPessoaJuridicaInexistente() {
        when(repository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.deletar(99L));

        assertEquals("Pessoa jurídica não encontrada.", exception.getMessage());

        verify(repository, times(1)).existsById(99L);
        verify(repository, never()).deleteById(anyLong());
    }
}

