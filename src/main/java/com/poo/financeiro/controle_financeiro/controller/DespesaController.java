package com.poo.financeiro.controle_financeiro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poo.financeiro.controle_financeiro.dto.DespesaDTO;
import com.poo.financeiro.controle_financeiro.model.Despesa;
import com.poo.financeiro.controle_financeiro.service.DespesaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/despesas")
@RequiredArgsConstructor
public class DespesaController {

    private final DespesaService service;

    @PostMapping
    public ResponseEntity<Despesa> cadastrar(@RequestBody @Valid DespesaDTO dto) {
        Despesa salva = service.salvar(dto);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public ResponseEntity<List<Despesa>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }
}
