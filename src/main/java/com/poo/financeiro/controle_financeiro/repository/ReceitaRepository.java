package com.poo.financeiro.controle_financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.poo.financeiro.controle_financeiro.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>, JpaSpecificationExecutor<Receita>  {
}
