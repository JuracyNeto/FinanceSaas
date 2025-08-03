package com.poo.financeiro.controle_financeiro.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DespesaDTO {

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser positivo")
    private BigDecimal valor;

    @NotNull(message = "Data da despesa é obrigatória")
    private LocalDate data;

    private String categoria;
}
