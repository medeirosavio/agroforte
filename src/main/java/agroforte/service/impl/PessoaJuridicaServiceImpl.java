package agroforte.service.impl;

import agroforte.dto.PessoaJuridicaDTO;
import agroforte.model.PessoaJuridica;
import agroforte.repository.PessoaJuridicaRepository;
import agroforte.service.PessoaJuridicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaJuridicaServiceImpl implements PessoaJuridicaService {

    private final PessoaJuridicaRepository repository;

    @Override
    @Transactional
    public PessoaJuridicaDTO criar(PessoaJuridicaDTO dto) {
        if (repository.existsByCnpj(dto.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado.");
        }

        PessoaJuridica entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PessoaJuridicaDTO buscarPorId(Long id) {
        PessoaJuridica entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa jurídica não encontrada."));
        return toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PessoaJuridicaDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PessoaJuridicaDTO atualizar(Long id, PessoaJuridicaDTO dto) {
        PessoaJuridica entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa jurídica não encontrada."));

        if (!entity.getCnpj().equals(dto.getCnpj()) && repository.existsByCnpj(dto.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado para outra empresa.");
        }

        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCelular(dto.getCelular());
        entity.setNacionalidade(dto.getNacionalidade());

        entity.setCnpj(dto.getCnpj());
        entity.setRazaoSocial(dto.getRazaoSocial());
        entity.setInscricaoEstadual(dto.getInscricaoEstadual());
        entity.setInscricaoMunicipal(dto.getInscricaoMunicipal());

        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pessoa jurídica não encontrada.");
        }
        repository.deleteById(id);
    }


    private PessoaJuridicaDTO toDTO(PessoaJuridica entity) {
        return PessoaJuridicaDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .celular(entity.getCelular())
                .nacionalidade(entity.getNacionalidade())
                .cnpj(entity.getCnpj())
                .razaoSocial(entity.getRazaoSocial())
                .inscricaoEstadual(entity.getInscricaoEstadual())
                .inscricaoMunicipal(entity.getInscricaoMunicipal())
                .build();
    }

    private PessoaJuridica toEntity(PessoaJuridicaDTO dto) {
        return PessoaJuridica.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .celular(dto.getCelular())
                .nacionalidade(dto.getNacionalidade())
                .cnpj(dto.getCnpj())
                .razaoSocial(dto.getRazaoSocial())
                .inscricaoEstadual(dto.getInscricaoEstadual())
                .inscricaoMunicipal(dto.getInscricaoMunicipal())
                .build();
    }
}
