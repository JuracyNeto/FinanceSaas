package com.poo.financeiro.controle_financeiro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poo.financeiro.controle_financeiro.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
