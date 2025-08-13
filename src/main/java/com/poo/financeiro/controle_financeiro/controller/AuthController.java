package com.poo.financeiro.controle_financeiro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poo.financeiro.controle_financeiro.dto.AuthRequestDTO;
import com.poo.financeiro.controle_financeiro.dto.AuthResponseDTO;
import com.poo.financeiro.controle_financeiro.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponseDTO> registrar(
            @RequestParam String nome,
            @RequestBody AuthRequestDTO request
    ) {
        return ResponseEntity.ok(authService.registrar(nome, request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.autenticar(request));
    }
}
