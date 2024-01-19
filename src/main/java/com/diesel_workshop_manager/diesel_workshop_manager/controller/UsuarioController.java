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
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.Usuario;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.UsuarioDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/usuario")
@RequiredArgsConstructor
public class UsuarioController {
  private final UsuarioService service;

  @GetMapping
  public ResponseEntity<List<Usuario>> listar() {
    return ResponseEntity.ok().body(service.listarUsuario());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    try {
      Usuario usuario = service.findById(id);
      if (usuario != null)
        return ResponseEntity.ok().body(usuario);
      else
        throw new RegraNegocioException("Usuario com ID " +
            id + " não encontrado");
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody UsuarioDTO dto) {
    try {
      Usuario usuario = service.saveUsuario(dto);
      return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody UsuarioDTO dto) {
    try {
      Usuario usuario = service.findById(id);
      if (usuario != null) {
        usuario = service.atualizarUsuario(id, dto);
        return ResponseEntity.ok().body(usuario);
      } else {
        throw new RegraNegocioException("Usuario com ID " +
            id + " não encontrado");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    try {
      Usuario usuario = service.findById(id);
      if (usuario != null) {
        service.deleteUser(usuario.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        throw new RegraNegocioException("Usuario com ID " +
            id + " não encontrado");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
