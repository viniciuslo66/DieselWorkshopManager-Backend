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
import com.diesel_workshop_manager.diesel_workshop_manager.models.cliente.Cliente;
import com.diesel_workshop_manager.diesel_workshop_manager.models.cliente.ClienteDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.service.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/cliente")
@RequiredArgsConstructor
public class ClienteController {
  private final ClienteService service;

  @GetMapping
  public ResponseEntity<List<Cliente>> listar() {
    return ResponseEntity.ok().body(service.listarClientes());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    try {
      Cliente cliente = service.findById(id);
      if (cliente != null)
        return ResponseEntity.ok().body(cliente);
      else
        throw new RegraNegocioException("Cliente com ID " +
            id + " não encontrado");
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody ClienteDTO dto) {
    try {
      Cliente cliente = service.saveCliente(dto);
      return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
    try {
      Cliente cliente = service.findById(id);
      if (cliente != null) {
        cliente = service.atualizarCliente(id, dto);
        return ResponseEntity.ok().body(cliente);
      } else {
        throw new RegraNegocioException("Cliente com ID " +
            id + " não encontrado");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    try {
      Cliente cliente = service.findById(id);
      if (cliente != null) {
        service.deletarCliente(cliente.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        throw new RegraNegocioException("Cliente com ID " +
            id + " não encontrado");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
