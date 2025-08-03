package com.poo.financeiro.controle_financeiro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poo.financeiro.controle_financeiro.dto.MetaDTO;
import com.poo.financeiro.controle_financeiro.model.MetaFinanceira;
import com.poo.financeiro.controle_financeiro.service.MetaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
public class MetaController {

    private final MetaService service;

    @PostMapping
    public ResponseEntity<MetaFinanceira> cadastrar(@RequestBody @Valid MetaDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<MetaFinanceira>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid MetaDTO dto) {
        return service.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return service.excluir(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
