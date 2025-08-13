package com.poo.financeiro.controle_financeiro.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SimulacaoLivreDTO {

    @NotNull(message = "Aporte mensal é obrigatório")
    @DecimalMin(value = "0.01", message = "Aporte deve ser maior que zero")
    private BigDecimal aporteMensal;

    @NotNull(message = "Taxa de juros mensal é obrigatória")
    @DecimalMin(value = "0.01", message = "Taxa de juros deve ser maior que zero")
    private BigDecimal taxaMensal; // em porcentagem, ex: 0.8 = 0.8%

    @NotNull(message = "Número de meses é obrigatório")
    @Min(1)
    private Integer meses;
}
