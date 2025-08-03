package com.poo.financeiro.controle_financeiro.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.poo.financeiro.controle_financeiro.model.Despesa;

//Criei essas consultas dinamicas para poder filtrar as despesas.  

public class DespesaSpecification {

    public static Specification<Despesa> dataMaiorOuIgual(LocalDate inicio) {
        return (root, query, cb) -> inicio != null ? cb.greaterThanOrEqualTo(root.get("data"), inicio) : null;
    }

    public static Specification<Despesa> dataMenorOuIgual(LocalDate fim) {
        return (root, query, cb) -> fim != null ? cb.lessThanOrEqualTo(root.get("data"), fim) : null;
    }

    public static Specification<Despesa> categoriaContem(String categoria) {
        return (root, query, cb) -> categoria != null ? cb.like(cb.lower(root.get("categoria")), "%" + categoria.toLowerCase() + "%") : null;
    }

    public static Specification<Despesa> descricaoContem(String q) {
        return (root, query, cb) -> q != null ? cb.like(cb.lower(root.get("descricao")), "%" + q.toLowerCase() + "%") : null;
    }
}

