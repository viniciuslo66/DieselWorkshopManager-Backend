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
import com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio.Relatorio;
import com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio.RelatorioDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.service.RelatorioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/relatorio")
@RequiredArgsConstructor
public class RelatorioController {
  private final RelatorioService service;

  @GetMapping
  public ResponseEntity<List<Relatorio>> listar() {
    return ResponseEntity.ok().body(service.listarRelatorios());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    try {
      Relatorio relatorio = service.findById(id);
      if (relatorio != null)
        return ResponseEntity.ok().body(relatorio);
      else
        throw new RegraNegocioException("Relatorio com ID " +
            id + " não encontrado");
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody RelatorioDTO dto) {
    try {
      Relatorio relatorio = service.saveRelatorio(dto);
      return new ResponseEntity<>(relatorio, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody RelatorioDTO dto) {
    try {
      Relatorio relatorio = service.findById(id);
      if (relatorio != null) {
        relatorio = service.ataulizarRelatorio(id, dto);
        return ResponseEntity.ok().body(relatorio);
      } else {
        throw new RegraNegocioException("Relatório com ID " +
            id + " não encontrado");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    try {
      Relatorio relatorio = service.findById(id);
      if (relatorio != null) {
        service.deletarRelatorio(relatorio.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        throw new RegraNegocioException("Relatório com ID " +
            id + " não encontrado");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
