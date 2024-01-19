package com.diesel_workshop_manager.diesel_workshop_manager.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.Endereco;
import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.EnderecoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.repository.EnderecoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EnderecoService {
  @Autowired
  EnderecoRepository repository;

  EnderecoService(EnderecoRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Endereco saveEndereco(EnderecoDTO dto) {
    Endereco endereco = converter(dto, null);
    return repository.save(endereco);
  }

  @Transactional
  public Endereco atualizarEndereco(Long id, EnderecoDTO dto) {
    Optional<Endereco> optional = repository.findById(id);

    if (!optional.isPresent()) {
      throw new EntityNotFoundException("Endereço não encontrado");
    }

    Endereco endereco = converter(dto, optional);
    return repository.save(endereco);
  }

  public List<Endereco> listaEnderecos() {
    return repository.findAll();
  }

  public Endereco findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Transactional
  public void deleteEndereco(Long id) {
    Optional<Endereco> optional = repository.findById(id);
    Endereco endereco = optional
        .orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado"));

    repository.delete(endereco);
  }

  private Endereco converter(EnderecoDTO dto, Optional<Endereco> optional) {
    Endereco endereco = Objects.nonNull(optional) ? optional.get() : new Endereco();

    endereco.setEstado(dto.getEstado());
    endereco.setCidade(dto.getCidade());
    endereco.setBairro(dto.getBairro());
    endereco.setCep(dto.getCep());
    endereco.setRua(dto.getRua());
    endereco.setNumero(dto.getNumero());

    return endereco;
  }
}
