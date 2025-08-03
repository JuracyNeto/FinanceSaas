package com.poo.financeiro.controle_financeiro.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReceitaDTO {

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "Data da receita é obrigatória")
    private LocalDate data;

    private String origem;
}
