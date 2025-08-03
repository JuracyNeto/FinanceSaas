package com.poo.financeiro.controle_financeiro.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.poo.financeiro.controle_financeiro.model.Receita;

public class ReceitaSpecification {

    public static Specification<Receita> dataMaiorOuIgual(LocalDate inicio) {
        return (root, query, cb) -> inicio != null ? cb.greaterThanOrEqualTo(root.get("data"), inicio) : null;
    }

    public static Specification<Receita> dataMenorOuIgual(LocalDate fim) {
        return (root, query, cb) -> fim != null ? cb.lessThanOrEqualTo(root.get("data"), fim) : null;
    }

    public static Specification<Receita> origemContem(String origem) {
        return (root, query, cb) -> origem != null ? cb.like(cb.lower(root.get("origem")), "%" + origem.toLowerCase() + "%") : null;
    }

    public static Specification<Receita> descricaoContem(String q) {
        return (root, query, cb) -> q != null ? cb.like(cb.lower(root.get("descricao")), "%" + q.toLowerCase() + "%") : null;
    }
}
