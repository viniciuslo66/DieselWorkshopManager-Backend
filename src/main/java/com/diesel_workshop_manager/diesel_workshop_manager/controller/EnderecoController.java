package com.diesel_workshop_manager.diesel_workshop_manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diesel_workshop_manager.diesel_workshop_manager.error.RegraNegocioException;
import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.Endereco;
import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.EnderecoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.service.EnderecoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/endereco")
@RequiredArgsConstructor
public class EnderecoController {
  private final EnderecoService service;

  @GetMapping
  public ResponseEntity<List<Endereco>> listar() {
    return ResponseEntity.ok().body(service.listaEnderecos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable("id") Long id) {
    try {
      Endereco endereco = service.findById(id);
      if (endereco != null)
        return ResponseEntity.ok().body(endereco);
      else
        throw new RegraNegocioException("Endereço com ID " +
            id + " não encontrado");
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody EnderecoDTO dto) {
    try {
      Endereco endereco = service.saveEndereco(dto);
      return new ResponseEntity<>(endereco, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody EnderecoDTO dto) {
    try {
      Endereco endereco = service.findById(id);
      if (endereco != null) {
        endereco = service.atualizarEndereco(id, dto);
        return ResponseEntity.ok().body(endereco);
      } else {
        throw new RegraNegocioException("Endereço com ID " + id + " não encontrado.");
      }

    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    try {
      Endereco endereco = service.findById(id);
      if (endereco != null) {
        service.deleteEndereco(endereco.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        throw new RegraNegocioException("Endereço com ID " + id + " não encontrado.");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
