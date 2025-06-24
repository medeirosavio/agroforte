package agroforte.controller;

import agroforte.dto.OperacaoDTO;
import agroforte.service.OperacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operacoes")
@RequiredArgsConstructor
public class OperacaoController {

    private final OperacaoService service;

    @PostMapping
    public ResponseEntity<OperacaoDTO> criar(@RequestBody @Valid OperacaoDTO dto) {
        OperacaoDTO created = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperacaoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<OperacaoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

