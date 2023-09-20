package com.diesel_workshop_manager.diesel_workshop_manager.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.Endereco;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.Usuario;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.UsuarioDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

  @Autowired
  UsuarioRepository repository;

  public UsuarioService(UsuarioRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Usuario saveUsuario(UsuarioDTO dto) {
    Usuario usuario = converter(dto, null);
    return repository.save(usuario);
  }

  @Transactional
  public Usuario atualizarUsuario(Long id, UsuarioDTO dto) {
    Optional<Usuario> optional = repository.findById(id);

    if (!optional.isPresent()) {
      throw new EntityNotFoundException("Usuario não encontrado");
    }

    Usuario usuario = converter(dto, optional);
    return repository.save(usuario);
  }

  @Transactional
  public void deleteUser(Long id) {
    Optional<Usuario> optional = repository.findById(id);
    Usuario user = optional.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

    repository.delete(user);
  }

  public List<Usuario> listarUsuario() {
    return repository.findAll();
  }

  public Usuario findById(Long id) {
    return repository.findById(id).orElseThrow();
  }

  // ------------------------------- converter -------------------------------

  private Usuario converter(UsuarioDTO dto, Optional<Usuario> optional) {
    Usuario usuario = Objects.nonNull(optional) ? optional.get() : new Usuario();

    usuario.setNomeUsuario(dto.getNomeUsuario());
    usuario.setCpf(dto.getCpf());
    usuario.setEmail(dto.getEmail());
    usuario.setTelefone(dto.getTelefone());
    usuario.setLogin(dto.getLogin());
    usuario.setSenha(dto.getSenha());

    if (dto.getEnderecoDTO() != null) {
      Endereco endereco = new Endereco();
      endereco.setEstado(dto.getEnderecoDTO().getEstado());
      endereco.setCidade(dto.getEnderecoDTO().getCidade());
      endereco.setBairro(dto.getEnderecoDTO().getBairro());
      endereco.setRua(dto.getEnderecoDTO().getRua());
      endereco.setCep(dto.getEnderecoDTO().getCep());
      endereco.setNumero(dto.getEnderecoDTO().getNumero());
      usuario.setEndereco(endereco);
    } else {
      usuario.setEndereco(null);
    }

    return usuario;
  }
}
