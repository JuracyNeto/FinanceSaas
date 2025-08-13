package com.poo.financeiro.controle_financeiro.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poo.financeiro.controle_financeiro.dto.SimulacaoLivreDTO;
import com.poo.financeiro.controle_financeiro.dto.SimulacaoResultadoDTO;
import com.poo.financeiro.controle_financeiro.service.SimuladorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/simulador")
@RequiredArgsConstructor
public class SimuladorController {

    private final SimuladorService service;

    @PostMapping
    public ResponseEntity<SimulacaoResultadoDTO> simularLivre(@RequestBody @Valid SimulacaoLivreDTO dto) {
        return ResponseEntity.ok(service.simularLivre(dto));
    }

    @GetMapping("/meta/{id}")
    public ResponseEntity<SimulacaoResultadoDTO> simularMeta(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0.8") BigDecimal taxa // taxa mensal em %
    ) {
        return ResponseEntity.ok(service.simularPorMeta(id, taxa));
    }
}
