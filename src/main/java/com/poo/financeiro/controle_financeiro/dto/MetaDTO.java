package com.poo.financeiro.controle_financeiro.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MetaDTO {

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    private String descricao;

    @NotNull(message = "Valor objetivo é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor objetivo deve ser maior que zero")
    private BigDecimal valorObjetivo;

    @DecimalMin(value = "0.00", message = "Valor atual não pode ser negativo")
    private BigDecimal valorAtual = BigDecimal.ZERO;

    private LocalDate dataLimite;
}
