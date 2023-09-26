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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diesel_workshop_manager.diesel_workshop_manager.error.RegraNegocioException;
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.Servico;
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.ServicoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.service.ServicoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/servico")
@RequiredArgsConstructor
public class ServicoController {
  private final ServicoService service;

  @GetMapping
  public ResponseEntity<List<Servico>> listar() {
    return ResponseEntity.ok().body(service.listarServicos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    try {
      Servico servico = service.findById(id);
      if (servico != null)
        return ResponseEntity.ok().body(servico);
      else
        throw new RegraNegocioException("Serviço com ID " + id + " não encontrado.");
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/ids")
  public ResponseEntity<List<?>> findServicosIds(@RequestParam List<Long> ids) {
    try {
      List<Servico> servicos = service.findServicosByIds(ids);
      if (servicos.isEmpty()) {
        return ResponseEntity.notFound().build();
      } else {
        return ResponseEntity.ok(servicos);
      }
    } catch (RegraNegocioException e) {
      throw new RegraNegocioException("Serviço com ID " + ids + " não encontrado.");
    }
  }

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody ServicoDTO servicoDTO) {
    try {
      Servico servico = service.saveServico(servicoDTO);
      return new ResponseEntity<>(servico, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody ServicoDTO servicoDTO) {
    try {
      Servico servico = service.findById(id);
      if (servico != null) {
        servico = service.updateServico(id, servicoDTO);
        return ResponseEntity.ok().body(servico);
      } else {
        throw new RegraNegocioException("Serviço com ID " + id + " não encontrado.");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    try {
      Servico servico = service.findById(id);
      if (servico != null) {
        service.deleteServico(servico.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        throw new RegraNegocioException("Serviço com ID " + id + " não encontrado.");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
