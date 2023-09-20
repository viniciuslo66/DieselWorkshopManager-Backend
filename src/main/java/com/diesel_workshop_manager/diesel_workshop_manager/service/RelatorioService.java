package com.diesel_workshop_manager.diesel_workshop_manager.service;

import java.util.List;
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
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.ServicoDTO;
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

    List<Long> ids = dto.getServicoDTOs().stream()
        .map(ServicoDTO::getId)
        .collect(Collectors.toList());

    if (ids.isEmpty()) {
      throw new RegraNegocioException("Nenhum serviço encontrado para os IDs informados.");
    }

    List<Servico> servicos = servicoService.findServicosByIds(ids);

    relatorio.setDataInicio(dto.getDataInicio());
    relatorio.setDataFim(dto.getDataFim());
    relatorio.setCliente(cliente);
    relatorio.setUsuario(usuario);
    relatorio.setVeiculo(veiculo);
    relatorio.setServicos(servicos);

    return relatorio;
  }

  
}
