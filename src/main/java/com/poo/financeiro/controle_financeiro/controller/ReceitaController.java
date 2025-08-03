package com.poo.financeiro.controle_financeiro.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poo.financeiro.controle_financeiro.dto.ReceitaDTO;
import com.poo.financeiro.controle_financeiro.model.Receita;
import com.poo.financeiro.controle_financeiro.service.ReceitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService service;

    @PostMapping
    public ResponseEntity<Receita> cadastrar(@RequestBody @Valid ReceitaDTO dto) {
        Receita salva = service.salvar(dto);
        return ResponseEntity.ok(salva);
    }

    // @GetMapping
    // public ResponseEntity<List<Receita>> listar() {
    //     return ResponseEntity.ok(service.listarTodas());
    // }


    @GetMapping
    public ResponseEntity<List<Receita>> listarComFiltros(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(required = false) String origem,
            @RequestParam(required = false) String q
    ) {
        return ResponseEntity.ok(service.filtrar(inicio, fim, origem, q));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid ReceitaDTO dto) {
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
