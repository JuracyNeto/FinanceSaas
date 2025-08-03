package com.poo.financeiro.controle_financeiro.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "metas_financeiras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column
    private String descricao;

    @Column(name = "valor_objetivo", nullable = false)
    private BigDecimal valorObjetivo;

    @Column(name = "valor_atual", nullable = false)
    private BigDecimal valorAtual;

    @Column(name = "data_limite")
    private LocalDate dataLimite;
}
