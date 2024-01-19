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
import com.diesel_workshop_manager.diesel_workshop_manager.models.orcamento.Orcamento;
import com.diesel_workshop_manager.diesel_workshop_manager.models.orcamento.OrcamentoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.service.OrcamentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/orcamento")
@RequiredArgsConstructor
public class OrcamentoController {
  private final OrcamentoService service;

  @GetMapping
  public ResponseEntity<List<Orcamento>> listar() {
    return ResponseEntity.ok().body(service.listaOrcamentos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    try {
      Orcamento orcamento = service.findById(id);
      if (orcamento != null)
        return ResponseEntity.ok().body(orcamento);
      else
        throw new RegraNegocioException("Orcamento com ID " +
            id + " não encontrado");
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody OrcamentoDTO dto) {
    try {
      Orcamento orcamento = service.saveOrcamento(dto);
      return new ResponseEntity<>(orcamento, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody OrcamentoDTO dto) {
    try {
      Orcamento orcamento = service.findById(id);
      if (orcamento != null) {
        orcamento = service.atualizarOrcamento(id, dto);
        return ResponseEntity.ok().body(orcamento);
      } else {
        throw new RegraNegocioException("Orcamento com ID " +
            id + " não encontrado");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    try {
      Orcamento orcamento = service.findById(id);
      if (orcamento != null) {
        service.deletarOrcamento(orcamento.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        throw new RegraNegocioException("Orcamento com ID " +
            id + " não encontrado");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
