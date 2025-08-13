package com.poo.financeiro.controle_financeiro.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDTO {

    private BigDecimal saldoAtual;

    private BigDecimal totalEntradasMesAtual;
    private BigDecimal totalSaidasMesAtual;
    private BigDecimal poupancaMesAtual;

    private BigDecimal poupancaMesAnterior;
    private BigDecimal diferencaPoupanca;
    private String comparativoMensagem;

    private BigDecimal totalInvestidoEmMetas;
    private BigDecimal progressoMedioMetas; // entre 0 e 1
}
