package agroforte.service.impl;

import agroforte.dto.PessoaFisicaDTO;
import agroforte.model.PessoaFisica;
import agroforte.repository.PessoaFisicaRepository;
import agroforte.service.PessoaFisicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaFisicaServiceImpl implements PessoaFisicaService {

    private final PessoaFisicaRepository repository;

    @Override
    @Transactional
    public PessoaFisicaDTO criar(PessoaFisicaDTO dto) {
        if (repository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("CPF já cadastrado.");
        }

        PessoaFisica entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PessoaFisicaDTO buscarPorId(Long id) {
        PessoaFisica entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa física não encontrada."));
        return toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PessoaFisicaDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PessoaFisicaDTO atualizar(Long id, PessoaFisicaDTO dto) {
        PessoaFisica entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa física não encontrada."));

        if (!entity.getCpf().equals(dto.getCpf()) && repository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("CPF já cadastrado para outra pessoa.");
        }

        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCelular(dto.getCelular());
        entity.setNacionalidade(dto.getNacionalidade());

        entity.setCpf(dto.getCpf());
        entity.setRg(dto.getRg());
        entity.setEstadoCivil(dto.getEstadoCivil());
        entity.setGenero(dto.getGenero());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setNomeMae(dto.getNomeMae());

        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pessoa física não encontrada.");
        }
        repository.deleteById(id);
    }


    private PessoaFisicaDTO toDTO(PessoaFisica entity) {
        return PessoaFisicaDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .celular(entity.getCelular())
                .nacionalidade(entity.getNacionalidade())
                .cpf(entity.getCpf())
                .rg(entity.getRg())
                .estadoCivil(entity.getEstadoCivil())
                .genero(entity.getGenero())
                .dataNascimento(entity.getDataNascimento())
                .nomeMae(entity.getNomeMae())
                .build();
    }

    private PessoaFisica toEntity(PessoaFisicaDTO dto) {
        return PessoaFisica.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .celular(dto.getCelular())
                .nacionalidade(dto.getNacionalidade())
                .cpf(dto.getCpf())
                .rg(dto.getRg())
                .estadoCivil(dto.getEstadoCivil())
                .genero(dto.getGenero())
                .dataNascimento(dto.getDataNascimento())
                .nomeMae(dto.getNomeMae())
                .build();
    }
}
