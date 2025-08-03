package com.poo.financeiro.controle_financeiro.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.poo.financeiro.controle_financeiro.dto.ReceitaDTO;
import com.poo.financeiro.controle_financeiro.model.Receita;
import com.poo.financeiro.controle_financeiro.repository.ReceitaRepository;
import static com.poo.financeiro.controle_financeiro.repository.ReceitaSpecification.dataMaiorOuIgual;
import static com.poo.financeiro.controle_financeiro.repository.ReceitaSpecification.dataMenorOuIgual;
import static com.poo.financeiro.controle_financeiro.repository.ReceitaSpecification.descricaoContem;
import static com.poo.financeiro.controle_financeiro.repository.ReceitaSpecification.origemContem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository repository;

    public Receita salvar(ReceitaDTO dto) {
        Receita receita = Receita.builder()
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .data(dto.getData())
                .origem(dto.getOrigem())
                .build();

        return repository.save(receita);
    }

    // public List<Receita> listarTodas() {
    //     return repository.findAll();
    // }


    public List<Receita> filtrar(LocalDate inicio, LocalDate fim, String origem, String q) {
        Specification<Receita> spec = dataMaiorOuIgual(inicio)
                .and(dataMenorOuIgual(fim))
                .and(origemContem(origem))
                .and(descricaoContem(q));

        return repository.findAll(spec);
    }


    public Optional<Receita> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Receita> atualizar(Long id, ReceitaDTO dto) {
        return repository.findById(id).map(r -> {
            r.setDescricao(dto.getDescricao());
            r.setValor(dto.getValor());
            r.setData(dto.getData());
            r.setOrigem(dto.getOrigem());
            return repository.save(r);
        });
    }

    public boolean excluir(Long id) {
        return repository.findById(id).map(r -> {
            repository.delete(r);
            return true;
        }).orElse(false);
    }
}
