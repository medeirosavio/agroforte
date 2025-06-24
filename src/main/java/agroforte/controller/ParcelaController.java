package agroforte.controller;

import agroforte.dto.ParcelaDTO;
import agroforte.service.ParcelaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parcelas")
@RequiredArgsConstructor
public class ParcelaController {

    private final ParcelaService service;

    @GetMapping("/operacao/{operacaoId}")
    public ResponseEntity<List<ParcelaDTO>> listarPorOperacao(@PathVariable Long operacaoId) {
        return ResponseEntity.ok(service.listarPorOperacao(operacaoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}

