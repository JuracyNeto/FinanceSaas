package com.poo.financeiro.controle_financeiro.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.poo.financeiro.controle_financeiro.dto.DashboardDTO;
import com.poo.financeiro.controle_financeiro.model.Despesa;
import com.poo.financeiro.controle_financeiro.model.MetaFinanceira;
import com.poo.financeiro.controle_financeiro.model.Receita;
import com.poo.financeiro.controle_financeiro.repository.DespesaRepository;
import com.poo.financeiro.controle_financeiro.repository.MetaRepository;
import com.poo.financeiro.controle_financeiro.repository.ReceitaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ReceitaRepository receitaRepo;
    private final DespesaRepository despesaRepo;
    private final MetaRepository metaRepo;

    public DashboardDTO gerarResumo() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate inicioMesAnterior = inicioMes.minusMonths(1);
        LocalDate fimMesAnterior = inicioMes.minusDays(1);

        List<Receita> receitasMes = receitaRepo.findAll().stream()
                .filter(r -> !r.getData().isBefore(inicioMes))
                .toList();

        List<Despesa> despesasMes = despesaRepo.findAll().stream()
                .filter(d -> !d.getData().isBefore(inicioMes))
                .toList();

        List<Receita> receitasAnterior = receitaRepo.findAll().stream()
                .filter(r -> !r.getData().isBefore(inicioMesAnterior) && !r.getData().isAfter(fimMesAnterior))
                .toList();

        List<Despesa> despesasAnterior = despesaRepo.findAll().stream()
                .filter(d -> !d.getData().isBefore(inicioMesAnterior) && !d.getData().isAfter(fimMesAnterior))
                .toList();

        BigDecimal totalReceitasMes = receitasMes.stream().map(Receita::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDespesasMes = despesasMes.stream().map(Despesa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal poupancaMes = totalReceitasMes.subtract(totalDespesasMes);

        BigDecimal totalReceitasAnt = receitasAnterior.stream().map(Receita::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDespesasAnt = despesasAnterior.stream().map(Despesa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal poupancaAnt = totalReceitasAnt.subtract(totalDespesasAnt);

        BigDecimal diferenca = poupancaMes.subtract(poupancaAnt);

        String mensagemComparativa;
        if (diferenca.compareTo(BigDecimal.ZERO) > 0) {
            mensagemComparativa = "Você poupou R$ " + diferenca.setScale(2, RoundingMode.HALF_UP) + " a mais em relação ao mês anterior.";
        } else if (diferenca.compareTo(BigDecimal.ZERO) < 0) {
            mensagemComparativa = "Você gastou R$ " + diferenca.abs().setScale(2, RoundingMode.HALF_UP) + " a mais em relação ao mês anterior.";
        } else {
            mensagemComparativa = "Sua poupança foi igual à do mês anterior.";
        }

        BigDecimal saldoTotal = receitaRepo.findAll().stream().map(Receita::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(despesaRepo.findAll().stream().map(Despesa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add));

        List<MetaFinanceira> metas = metaRepo.findAll();
        BigDecimal totalInvestido = metas.stream().map(MetaFinanceira::getValorAtual).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal progressoMedio = metas.isEmpty() ? BigDecimal.ZERO :
                metas.stream()
                        .map(m -> m.getValorAtual().divide(m.getValorObjetivo(), 4, RoundingMode.HALF_UP))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(metas.size()), 4, RoundingMode.HALF_UP);

        return DashboardDTO.builder()
                .saldoAtual(saldoTotal.setScale(2, RoundingMode.HALF_UP))
                .totalEntradasMesAtual(totalReceitasMes.setScale(2, RoundingMode.HALF_UP))
                .totalSaidasMesAtual(totalDespesasMes.setScale(2, RoundingMode.HALF_UP))
                .poupancaMesAtual(poupancaMes.setScale(2, RoundingMode.HALF_UP))
                .poupancaMesAnterior(poupancaAnt.setScale(2, RoundingMode.HALF_UP))
                .diferencaPoupanca(diferenca.setScale(2, RoundingMode.HALF_UP))
                .comparativoMensagem(mensagemComparativa)
                .totalInvestidoEmMetas(totalInvestido.setScale(2, RoundingMode.HALF_UP))
                .progressoMedioMetas(progressoMedio.setScale(4, RoundingMode.HALF_UP))
                .build();
    }
}
