package com.poo.financeiro.controle_financeiro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String senha;
}
