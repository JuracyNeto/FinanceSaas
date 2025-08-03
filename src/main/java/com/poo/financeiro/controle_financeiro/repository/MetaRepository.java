package com.poo.financeiro.controle_financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poo.financeiro.controle_financeiro.model.MetaFinanceira;

@Repository
public interface MetaRepository extends JpaRepository<MetaFinanceira, Long> {
}
