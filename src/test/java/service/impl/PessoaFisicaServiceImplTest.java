package service.impl;

import agroforte.dto.PessoaFisicaDTO;
import agroforte.model.PessoaFisica;
import agroforte.repository.PessoaFisicaRepository;
import agroforte.service.impl.PessoaFisicaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class PessoaFisicaServiceImplTest {

    @InjectMocks
    private PessoaFisicaServiceImpl service;

    @Mock
    private PessoaFisicaRepository repository;

    private PessoaFisica pessoaFisica;
    private PessoaFisicaDTO pessoaFisicaDTO;

    @BeforeEach
    void setUp() {
        pessoaFisica = PessoaFisica.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .cpf("12345678900")
                .rg("1234567")
                .celular("11999999999")
                .estadoCivil("Solteiro")
                .genero("Masculino")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .nomeMae("Maria Silva")
                .nacionalidade("Brasileiro")
                .build();

        pessoaFisicaDTO = PessoaFisicaDTO.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .cpf("12345678900")
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
    void deveCriarPessoaFisicaComSucesso() {
        when(repository.existsByCpf(pessoaFisicaDTO.getCpf())).thenReturn(false);
        when(repository.save(any(PessoaFisica.class))).thenReturn(pessoaFisica);

        PessoaFisicaDTO resultado = service.criar(pessoaFisicaDTO);

        assertNotNull(resultado);
        assertEquals(pessoaFisicaDTO.getNome(), resultado.getNome());
        assertEquals(pessoaFisicaDTO.getCpf(), resultado.getCpf());

        verify(repository, times(1)).existsByCpf(pessoaFisicaDTO.getCpf());
        verify(repository, times(1)).save(any(PessoaFisica.class));
    }

    @Test
    void deveLancarExcecaoAoCriarCpfDuplicado() {
        when(repository.existsByCpf(pessoaFisicaDTO.getCpf())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.criar(pessoaFisicaDTO));

        assertEquals("CPF já cadastrado.", exception.getMessage());

        verify(repository, times(1)).existsByCpf(pessoaFisicaDTO.getCpf());
        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarPessoaFisicaPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaFisica));

        PessoaFisicaDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoPessoaNaoEncontrada() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.buscarPorId(99L));

        assertEquals("Pessoa física não encontrada.", exception.getMessage());

        verify(repository, times(1)).findById(99L);
    }

    @Test
    void deveListarTodasPessoasFisicas() {
        when(repository.findAll()).thenReturn(List.of(pessoaFisica));

        List<PessoaFisicaDTO> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());

        verify(repository, times(1)).findAll();
    }

    @Test
    void deveAtualizarPessoaFisicaComSucesso() {
        Long id = 1L;
        String cpf = "12345678900";

        PessoaFisicaDTO dto = PessoaFisicaDTO.builder()
                .nome("João Silva")
                .cpf(cpf) // CPF igual ao da entidade
                .build();

        PessoaFisica pessoaOriginal = PessoaFisica.builder()
                .id(id)
                .cpf(cpf) // Mesmo CPF
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(pessoaOriginal));
        when(repository.save(any(PessoaFisica.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PessoaFisicaDTO atualizado = service.atualizar(id, dto);

        assertNotNull(atualizado);
        assertEquals(dto.getNome(), atualizado.getNome());

        verify(repository, times(1)).findById(id);
        verify(repository, never()).existsByCpf(anyString()); // Não deve chamar porque CPF não mudou
        verify(repository, times(1)).save(any(PessoaFisica.class));
    }


    @Test
    void deveLancarExcecaoAoAtualizarComCpfDuplicado() {
        // CPF original da pessoa no banco
        String cpfOriginal = "12345678900";
        // CPF novo que vai causar conflito
        String cpfNovo = "09876543211";

        PessoaFisicaDTO dtoComCpfDuplicado = PessoaFisicaDTO.builder()
                .nome("João Silva")
                .cpf(cpfNovo) // CPF diferente do original
                .build();

        PessoaFisica pessoaOriginal = PessoaFisica.builder()
                .id(1L)
                .cpf(cpfOriginal) // CPF atual no banco
                .build();

        // Mock do findById retorna a pessoa original com cpfOriginal
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaOriginal));

        // Mock do existsByCpf para o cpfNovo (diferente) retorna true, indicando conflito
        when(repository.existsByCpf(cpfNovo)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.atualizar(1L, dtoComCpfDuplicado));

        assertEquals("CPF já cadastrado para outra pessoa.", exception.getMessage());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).existsByCpf(cpfNovo);
        verify(repository, never()).save(any());
    }

    @Test
    void deveDeletarPessoaFisicaComSucesso() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarPessoaFisicaInexistente() {
        when(repository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.deletar(99L));

        assertEquals("Pessoa física não encontrada.", exception.getMessage());

        verify(repository, times(1)).existsById(99L);
        verify(repository, never()).deleteById(anyLong());
    }
}
