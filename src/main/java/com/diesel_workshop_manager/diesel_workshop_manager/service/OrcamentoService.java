package com.diesel_workshop_manager.diesel_workshop_manager.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diesel_workshop_manager.diesel_workshop_manager.error.RegraNegocioException;
import com.diesel_workshop_manager.diesel_workshop_manager.models.orcamento.Orcamento;
import com.diesel_workshop_manager.diesel_workshop_manager.models.orcamento.OrcamentoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio.Relatorio;
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.Servico;
import com.diesel_workshop_manager.diesel_workshop_manager.repository.OrcamentoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@SuppressWarnings("null")
@Service
public class OrcamentoService {

  @Autowired
  OrcamentoRepository repository;
  @Autowired
  RelatorioService relatorioService;
  @Autowired
  ServicoService servicoService;

  OrcamentoService(OrcamentoRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Orcamento saveOrcamento(OrcamentoDTO dto) {
    Orcamento orcamento = converter(dto, null);
    return repository.save(orcamento);
  }

  @Transactional
  public Orcamento atualizarOrcamento(Long id, OrcamentoDTO dto) {
    Optional<Orcamento> optional = repository.findById(id);

    if (!optional.isPresent()) {
      throw new EntityNotFoundException("Orcamento não encontrado");
    }

    Orcamento orcamento = converter(dto, optional);
    return repository.save(orcamento);
  }

  @Transactional
  public void deletarOrcamento(Long id) {
    Optional<Orcamento> optional = repository.findById(id);
    Orcamento orcamento = optional
        .orElseThrow(() -> new EntityNotFoundException("Orçamento não encontrado"));

    repository.delete(orcamento);
  }

  public List<Orcamento> listaOrcamentos() {
    return repository.findAll();
  }

  public Orcamento findById(Long id) {
    return repository.findById(id).orElseThrow();
  }

  // ------------------------------- converter -------------------------------

  private Orcamento converter(OrcamentoDTO dto, Optional<Orcamento> optional) {
    Orcamento orcamento = optional.orElse(new Orcamento());

    List<Long> ids = dto.getRelatorios();

    if (ids.isEmpty()) {
      throw new RegraNegocioException("Nenhum relatório encontrado para os IDs");
    }

    List<Relatorio> relatorios = relatorioService.findRelatoriosByIds(ids);
    orcamento.setRelatorios(relatorios);

    if (!relatorios.isEmpty()) {
      calcularSubtotal(orcamento, relatorios);
    } else {
      ajustarValoresOrcamentoVazio(orcamento);
    }

    return orcamento;
  }

  private void calcularSubtotal(Orcamento orcamento, List<Relatorio> relatorios) {
    double totalPrecoServicos = 0.0;

    for (Relatorio relatorio : relatorios) {
      totalPrecoServicos += calcularPrecoServicos(relatorio);
      ajustarDatas(orcamento, relatorio);
    }

    orcamento.setSubtotal(totalPrecoServicos);
    orcamento.setDesconto(totalPrecoServicos * 0.10);
    orcamento.setTotalPrice(totalPrecoServicos - (totalPrecoServicos * 0.10));
  }

  private double calcularPrecoServicos(Relatorio relatorio) {
    double precoTotal = 0.0;
    Map<Servico, Integer> servicos = relatorio.getServicos();

    if (servicos != null) {
      for (Map.Entry<Servico, Integer> entry : servicos.entrySet()) {
        Servico servico = entry.getKey();
        Integer quantidade = entry.getValue();
        double preco = servico.getPrice();
        precoTotal += preco * quantidade;
      }
    }

    return precoTotal;
  }

  private void ajustarDatas(Orcamento orcamento, Relatorio relatorio) {
    if (relatorio.getDataInicio().before(orcamento.getDataInicio())) {
      orcamento.setDataInicio(relatorio.getDataInicio());
    }
    if (relatorio.getDataFim().after(orcamento.getDataFim())) {
      orcamento.setDataFim(relatorio.getDataFim());
    }
  }

  private void ajustarValoresOrcamentoVazio(Orcamento orcamento) {
    orcamento.setSubtotal(null);
    orcamento.setDesconto(null);
    orcamento.setTotalPrice(null);
    orcamento.setDataInicio(null);
    orcamento.setDataFim(null);
    throw new RegraNegocioException("A lista de relatórios está vazia");
  }
}
