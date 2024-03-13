package com.diesel_workshop_manager.diesel_workshop_manager.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo.Veiculo;
import com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo.VeiculoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.repository.VeiculoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@SuppressWarnings("null")
@Service
public class VeiculoService {
  @Autowired
  VeiculoRepository repository;

  VeiculoService(VeiculoRepository repository) {
    this.repository = repository;
  }

  
  @Transactional
  public Veiculo saveVeiculo(VeiculoDTO dto) {
    Veiculo veiculo = converter(dto, null);
    return repository.save(veiculo);
  }

  @Transactional
  public Veiculo atualizarVeiculo(Long id, VeiculoDTO dto) {
    Optional<Veiculo> optional = repository.findById(id);

    if (!optional.isPresent()) {
      throw new EntityNotFoundException("Veiculo não encontrado");
    }

    Veiculo veiculo = converter(dto, optional);
    return repository.save(veiculo);
  }

  @Transactional
  public void deletarVeiculo(Long id) {
    Optional<Veiculo> optional = repository.findById(id);
    Veiculo veiculo = optional.orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

    repository.delete(veiculo);
  }

  public List<Veiculo> listarVeiculos() {
    return repository.findAll();
  }

  public Veiculo findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public List<Veiculo> findByIds(List<Long> ids) {
    return repository.findByIdIn(ids);
  }

  // ------------------------------- converter -------------------------------

  private Veiculo converter(VeiculoDTO dto, Optional<Veiculo> optional) {
    Veiculo veiculo = Objects.nonNull(optional) ? optional.get() : new Veiculo();

    veiculo.setNomeVeiculo(dto.getNomeVeiculo());
    veiculo.setPrefixo(dto.getPrefixo());
    veiculo.setHorimetro(dto.getHorimetro());

    return veiculo;
  }
}
