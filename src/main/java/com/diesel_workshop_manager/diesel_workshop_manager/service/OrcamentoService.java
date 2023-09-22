package com.diesel_workshop_manager.diesel_workshop_manager.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.diesel_workshop_manager.diesel_workshop_manager.error.RegraNegocioException;
import com.diesel_workshop_manager.diesel_workshop_manager.models.orcamento.Orcamento;
import com.diesel_workshop_manager.diesel_workshop_manager.models.orcamento.OrcamentoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio.Relatorio;
import com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio.RelatorioDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.ServicoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.repository.OrcamentoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public class OrcamentoService {

  @Autowired
  OrcamentoRepository repository;
  @Autowired
  RelatorioService relatorioService;

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
    Orcamento orcamento = Objects.nonNull(optional) ? optional.get() : new Orcamento();

    List<Long> ids = dto.getRelatorioDTOs().stream()
        .map(RelatorioDTO::getId)
        .collect(Collectors.toList());

    if (ids.isEmpty())
      throw new RegraNegocioException("Nenhum relátório encontrado para os IDs");

    List<Relatorio> relatorios = relatorioService.findRelatoriosByIds(ids);

    orcamento.setRelatorios(relatorios);

    if (!dto.getRelatorioDTOs().isEmpty()) {
      // Inicialize as datas com a primeira data do primeiro relatório
      orcamento.setDataInicio(dto.getRelatorioDTOs().get(0).getDataInicio());
      orcamento.setDataFim(dto.getRelatorioDTOs().get(0).getDataFim());

      Double totalPrecoServicos = 0.0;

      /*
       * Itere pela lista de relatórios para encontrar a data mais antiga (dataInicio)
       * e a data mais recente (dataFim)
       */
      for (RelatorioDTO relatorioDTO : dto.getRelatorioDTOs()) {

        if (relatorioDTO.getDataInicio().before(orcamento.getDataInicio())) {
          orcamento.setDataInicio(relatorioDTO.getDataInicio());
        }
        if (relatorioDTO.getDataFim().after(orcamento.getDataFim())) {
          orcamento.setDataFim(relatorioDTO.getDataFim());
        }

        // Iterar pela lista de ServicoDTOs dentro do RelatorioDTO e somar os preços
        if (relatorioDTO.getServicoDTOs() != null) {
          for (Map.Entry<ServicoDTO, Integer> entry : relatorioDTO.getServicoDTOs().entrySet()) {
            ServicoDTO servicoDTO = entry.getKey();
            Integer quantidade = entry.getValue();
            totalPrecoServicos += servicoDTO.getPrice() * quantidade;
          }
        }

        orcamento.setSubtotal(totalPrecoServicos);
        orcamento.setDesconto(totalPrecoServicos * 0.10);
        orcamento.setTotalPrice(totalPrecoServicos + (totalPrecoServicos * 0.10));
      }
    } else {
      orcamento.setSubtotal(null);
      orcamento.setDesconto(null);
      orcamento.setTotalPrice(null);
      orcamento.setDataInicio(null);
      orcamento.setDataFim(null);
      throw new RegraNegocioException("A lista de relatórios está vazia");
    }

    return orcamento;
  }
}
