package com.poo.financeiro.controle_financeiro.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimulacaoResultadoDTO {
    private BigDecimal valorFinal;
    private BigDecimal valorInvestido;
    private BigDecimal jurosObtidos;
    private BigDecimal aporteMensal;
    private BigDecimal taxaMensal;
    private Integer meses;
    private String recomendacao; // opcional, pensamos para o caso de metas
}
