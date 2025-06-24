package agroforte.controller;

import agroforte.dto.PessoaJuridicaDTO;
import agroforte.service.PessoaJuridicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas-juridicas")
@RequiredArgsConstructor
public class PessoaJuridicaController {

    private final PessoaJuridicaService service;

    @PostMapping
    public ResponseEntity<PessoaJuridicaDTO> criar(@RequestBody @Valid PessoaJuridicaDTO dto) {
        PessoaJuridicaDTO created = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaJuridicaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PessoaJuridicaDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaJuridicaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PessoaJuridicaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

