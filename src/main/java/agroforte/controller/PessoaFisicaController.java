package agroforte.controller;

import agroforte.dto.PessoaFisicaDTO;
import agroforte.service.PessoaFisicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas-fisicas")
@RequiredArgsConstructor
public class PessoaFisicaController {

    private final PessoaFisicaService service;

    @PostMapping
    public ResponseEntity<PessoaFisicaDTO> criar(@RequestBody @Valid PessoaFisicaDTO dto) {
        PessoaFisicaDTO created = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaFisicaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PessoaFisicaDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaFisicaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PessoaFisicaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

