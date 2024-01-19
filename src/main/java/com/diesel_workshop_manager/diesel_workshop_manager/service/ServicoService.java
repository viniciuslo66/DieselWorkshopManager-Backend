package com.diesel_workshop_manager.diesel_workshop_manager.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.Servico;
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.ServicoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.repository.ServicoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ServicoService {

  @Autowired
  private ServicoRepository repository;

  ServicoService(ServicoRepository repository) {
    this.repository = repository;
  }
  
  public List<Servico> listarServicos() {
    return repository.findAll();
  }

  public Servico findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public List<Servico> findServicosByIds(List<Long> ids) {
    return repository.findByIdIn(ids);
  }
  
  @Transactional
  public Servico saveServico(ServicoDTO serviceDto) {
    Servico servico = converter(serviceDto, null);
    return repository.save(servico);
  }

  @Transactional
  public Servico updateServico(Long id, ServicoDTO serviceDto) {
    Optional<Servico> optional = repository.findById(id);

    if (!optional.isPresent()) {
      throw new EntityNotFoundException("Servico não encontrado");
    }

    Servico servico = converter(serviceDto, optional);
    return repository.save(servico);
  }

  @Transactional
  public void deleteServico(Long id) {
    Optional<Servico> optional = repository.findById(id);
    Servico servico = optional
        .orElseThrow(() -> new EntityNotFoundException("Servico não encontrado"));

    repository.delete(servico);
  }


  // ------------------------------- converter -------------------------------

  private Servico converter(ServicoDTO dto, Optional<Servico> optional) {
    Servico servico = Objects.nonNull(optional) ? optional.get() : new Servico();

    servico.setNomeServico(dto.getNomeServico());
    servico.setPrice(dto.getPrice());

    return servico;
  }
}
