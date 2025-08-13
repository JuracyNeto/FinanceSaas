package com.poo.financeiro.controle_financeiro.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poo.financeiro.controle_financeiro.dto.AuthRequestDTO;
import com.poo.financeiro.controle_financeiro.dto.AuthResponseDTO;
import com.poo.financeiro.controle_financeiro.model.Usuario;
import com.poo.financeiro.controle_financeiro.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponseDTO registrar(String nome, AuthRequestDTO request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(nome)
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .build();

        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(usuario.getEmail());
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO autenticar(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String token = jwtService.generateToken(usuario.getEmail());
        return new AuthResponseDTO(token);
    }
}
