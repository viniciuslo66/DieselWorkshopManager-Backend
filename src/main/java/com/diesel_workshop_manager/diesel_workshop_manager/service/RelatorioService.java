package com.diesel_workshop_manager.diesel_workshop_manager.service;

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

@SuppressWarnings("null")
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

    Cliente cliente = clienteService.findById(dto.getCliente());
    Usuario usuario = usuarioService.findById(dto.getUsuario());
    Veiculo veiculo = veiculoService.findById(dto.getVeiculo());

    Map<Long, Integer> idsQuantidades = dto.getServicos();

    if (idsQuantidades.isEmpty()) {
      throw new RegraNegocioException("Nenhum serviço encontrado para os IDs informados.");
    }

    // Obter serviços do banco de dados com base nos IDs
    List<Servico> servicos = idsQuantidades.keySet().stream()
        .map(servicoService::findById)
        .peek(servico -> {
          if (servico == null) {
            throw new RegraNegocioException("Serviço não encontrado para o ID: " + servico.getId());
          }
        })
        .collect(Collectors.toList());

    // Criar mapa de serviços e suas quantidades
    Map<Servico, Integer> servicos_quantidades = servicos.stream()
        .collect(Collectors.toMap(
            servico -> servico,
            servico -> idsQuantidades.get(servico.getId())));

    relatorio.setDataInicio(dto.getDataInicio());
    relatorio.setDataFim(dto.getDataFim());
    relatorio.setCliente(cliente);
    relatorio.setUsuario(usuario);
    relatorio.setVeiculo(veiculo);
    relatorio.setServicos(servicos_quantidades);

    return relatorio;
  }

}
