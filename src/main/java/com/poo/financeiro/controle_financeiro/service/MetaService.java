package com.poo.financeiro.controle_financeiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.poo.financeiro.controle_financeiro.dto.MetaDTO;
import com.poo.financeiro.controle_financeiro.model.MetaFinanceira;
import com.poo.financeiro.controle_financeiro.repository.MetaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaRepository repository;

    public MetaFinanceira salvar(MetaDTO dto) {
        MetaFinanceira meta = MetaFinanceira.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .valorObjetivo(dto.getValorObjetivo())
                .valorAtual(dto.getValorAtual() != null ? dto.getValorAtual() : java.math.BigDecimal.ZERO)
                .dataLimite(dto.getDataLimite())
                .build();

        return repository.save(meta);
    }

    public List<MetaFinanceira> listarTodas() {
        return repository.findAll();
    }

    public Optional<MetaFinanceira> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<MetaFinanceira> atualizar(Long id, MetaDTO dto) {
        return repository.findById(id).map(meta -> {
            meta.setTitulo(dto.getTitulo());
            meta.setDescricao(dto.getDescricao());
            meta.setValorObjetivo(dto.getValorObjetivo());
            meta.setValorAtual(dto.getValorAtual() != null ? dto.getValorAtual() : meta.getValorAtual());
            meta.setDataLimite(dto.getDataLimite());
            return repository.save(meta);
        });
    }

    public boolean excluir(Long id) {
        return repository.findById(id).map(meta -> {
            repository.delete(meta);
            return true;
        }).orElse(false);
    }
}
