package com.poo.financeiro.controle_financeiro.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.poo.financeiro.controle_financeiro.dto.SimulacaoLivreDTO;
import com.poo.financeiro.controle_financeiro.dto.SimulacaoResultadoDTO;
import com.poo.financeiro.controle_financeiro.model.MetaFinanceira;
import com.poo.financeiro.controle_financeiro.repository.MetaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimuladorService {

    private final MetaRepository metaRepository;

    public SimulacaoResultadoDTO simularLivre(SimulacaoLivreDTO dto) {
        BigDecimal i = dto.getTaxaMensal().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        BigDecimal fator = (BigDecimal.ONE.add(i)).pow(dto.getMeses());
        BigDecimal valorFinal = dto.getAporteMensal()
                .multiply(fator.subtract(BigDecimal.ONE))
                .divide(i, 2, RoundingMode.HALF_UP);

        BigDecimal investido = dto.getAporteMensal().multiply(BigDecimal.valueOf(dto.getMeses()));
        BigDecimal juros = valorFinal.subtract(investido);

        return SimulacaoResultadoDTO.builder()
                .valorFinal(valorFinal)
                .valorInvestido(investido)
                .jurosObtidos(juros)
                .aporteMensal(dto.getAporteMensal())
                .taxaMensal(dto.getTaxaMensal())
                .meses(dto.getMeses())
                .build();
    }

    public SimulacaoResultadoDTO simularPorMeta(Long metaId, BigDecimal taxaMensal) {
        MetaFinanceira meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada"));

        BigDecimal PV = meta.getValorAtual();
        BigDecimal FV = meta.getValorObjetivo();
        LocalDate hoje = LocalDate.now();
        LocalDate limite = meta.getDataLimite() != null ? meta.getDataLimite() : hoje.plusMonths(12);
        long n = ChronoUnit.MONTHS.between(hoje, limite);
        if (n <= 0) throw new IllegalArgumentException("Data limite inválida");

        BigDecimal i = taxaMensal.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        BigDecimal fator = (BigDecimal.ONE.add(i)).pow((int) n);

        BigDecimal parte1 = FV.subtract(PV.multiply(fator));
        BigDecimal aporte = parte1.multiply(i).divide(fator.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        BigDecimal valorInvestido = aporte.multiply(BigDecimal.valueOf(n));
        BigDecimal valorFinal = PV.multiply(fator).add(aporte.multiply(fator.subtract(BigDecimal.ONE)).divide(i, 2, RoundingMode.HALF_UP));
        BigDecimal juros = valorFinal.subtract(valorInvestido).subtract(PV);

        return SimulacaoResultadoDTO.builder()
                .valorFinal(valorFinal)
                .valorInvestido(valorInvestido.add(PV))
                .jurosObtidos(juros)
                .aporteMensal(aporte)
                .taxaMensal(taxaMensal)
                .meses((int) n)
                .recomendacao("Invista R$" + aporte + "/mês até " + limite + " com rendimento de " + taxaMensal + "% a.m.")
                .build();
    }
}
