package com.diesel_workshop_manager.diesel_workshop_manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diesel_workshop_manager.diesel_workshop_manager.error.RegraNegocioException;
import com.diesel_workshop_manager.diesel_workshop_manager.models.cliente.Cliente;
import com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio.Relatorio;
import com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio.RelatorioDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.Servico;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.Usuario;
import com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo.Veiculo;
import com.diesel_workshop_manager.diesel_workshop_manager.repository.RelatorioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RelatorioService {

  @Autowired
  RelatorioRepository repository;
  @Autowired
  ClienteService clienteService;
  @Autowired
  UsuarioService usuarioService;
  @Autowired
  VeiculoService veiculoService;
  @Autowired
  ServicoService servicoService;

  RelatorioService(RelatorioRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Relatorio saveRelatorio(RelatorioDTO dto) {
    Relatorio relatorio = converter(dto, null);
    return repository.save(relatorio);
  }

  @Transactional
  public Relatorio ataulizarRelatorio(Long id, RelatorioDTO dto) {
    Optional<Relatorio> optional = repository.findById(id);

    if (!optional.isPresent()) {
      throw new EntityNotFoundException("Relatório não encontrado");
    }

    Relatorio relatorio = converter(dto, optional);
    return repository.save(relatorio);
  }

  @Transactional
  public void deletarRelatorio(Long id) {
    Optional<Relatorio> optional = repository.findById(id);
    Relatorio relatorio = optional.orElseThrow(() -> new EntityNotFoundException("Relatório não encontrado"));

    repository.delete(relatorio);
  }

  public List<Relatorio> listarRelatorios() {
    return repository.findAll();
  }

  public Relatorio findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Relatório não encontrado"));
  }

  public List<Relatorio> findRelatoriosByIds(List<Long> ids) {
    return repository.findByIdIn(ids);
  }

  // ------------------------------- converter -------------------------------

  private Relatorio converter(RelatorioDTO dto, Optional<Relatorio> optional) {
    Relatorio relatorio = Objects.nonNull(optional) ? optional.get() : new Relatorio();

    Cliente cliente = clienteService.findById(dto.getClienteDTO().getId());
    Usuario usuario = usuarioService.findById(dto.getUsuarioDTO().getId());
    Veiculo veiculo = veiculoService.findById(dto.getVeiculoDTO().getId());

    Map<Long, Integer> idsQuantidades = dto.getServicoDTOs().entrySet().stream()
        .collect(Collectors.toMap(
            entry -> entry.getKey().getId(), // Obtém o ID do ServicoDTO
            Map.Entry::getValue // Obtém a quantidade do ServicoDTO
        ));

    if (idsQuantidades.isEmpty()) {
      throw new RegraNegocioException("Nenhum serviço encontrado para os IDs informados.");
    }

    List<Servico> servicos = servicoService.findServicosByIds(new ArrayList<>(idsQuantidades.keySet()));

    // Configure o mapa quantidadeServicos em Relatorio com base em idsQuantidades
    Map<Servico, Integer> servicosQuantidade = new HashMap<>();
    for (Servico servico : servicos) {
      servicosQuantidade.put(servico, idsQuantidades.get(servico.getId()));
    }

    relatorio.setDataInicio(dto.getDataInicio());
    relatorio.setDataFim(dto.getDataFim());
    relatorio.setCliente(cliente);
    relatorio.setUsuario(usuario);
    relatorio.setVeiculo(veiculo);
    relatorio.setServicos(servicosQuantidade);

    return relatorio;
  }

}
