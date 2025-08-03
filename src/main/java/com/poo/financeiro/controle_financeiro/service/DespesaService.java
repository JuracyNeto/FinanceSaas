package com.poo.financeiro.controle_financeiro.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification; // Chamando as consultas dinamicas que criamos para despeas
import org.springframework.stereotype.Service;

import com.poo.financeiro.controle_financeiro.dto.DespesaDTO;
import com.poo.financeiro.controle_financeiro.model.Despesa;
import com.poo.financeiro.controle_financeiro.repository.DespesaRepository;
import static com.poo.financeiro.controle_financeiro.repository.DespesaSpecification.categoriaContem;
import static com.poo.financeiro.controle_financeiro.repository.DespesaSpecification.dataMaiorOuIgual;
import static com.poo.financeiro.controle_financeiro.repository.DespesaSpecification.dataMenorOuIgual;
import static com.poo.financeiro.controle_financeiro.repository.DespesaSpecification.descricaoContem;

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

    // Comentando o método listarTodas, pois não é necessário. Já temos o listar com filtros agora. 
    // public List<Despesa> listarTodas() {
    //     return repository.findAll();
    // }

    public Optional<Despesa> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Despesa> atualizar(Long id, DespesaDTO dto) {
    return repository.findById(id).map(d -> {
        d.setDescricao(dto.getDescricao());
        d.setValor(dto.getValor());
        d.setData(dto.getData());
        d.setCategoria(dto.getCategoria());
        return repository.save(d);
        });
    }

    public boolean excluir(Long id) {
    return repository.findById(id).map(d -> {
        repository.delete(d);
        return true;
        }).orElse(false);
    }


    public List<Despesa> filtrar(LocalDate inicio, LocalDate fim, String categoria, String q) {
        Specification<Despesa> spec = dataMaiorOuIgual(inicio)
            .and(dataMenorOuIgual(fim))
            .and(categoriaContem(categoria))
            .and(descricaoContem(q));
        return repository.findAll(spec);
    }



}

