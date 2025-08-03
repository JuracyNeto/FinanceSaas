package com.poo.financeiro.controle_financeiro.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.poo.financeiro.controle_financeiro.dto.DespesaDTO;
import com.poo.financeiro.controle_financeiro.model.Despesa;
import com.poo.financeiro.controle_financeiro.repository.DespesaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DespesaService {

    private final DespesaRepository repository;

    public Despesa salvar(DespesaDTO dto) {
        Despesa despesa = Despesa.builder()
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .data(dto.getData())
                .categoria(dto.getCategoria())
                .build();

        return repository.save(despesa);
    }

    public List<Despesa> listarTodas() {
        return repository.findAll();
    }
}

