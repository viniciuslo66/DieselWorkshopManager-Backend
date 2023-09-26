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
import com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo.Veiculo;
import com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo.VeiculoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.service.VeiculoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/veiculo")
@RequiredArgsConstructor
public class VeiculoController {
  private final VeiculoService service;

  @GetMapping
  public ResponseEntity<List<Veiculo>> listar() {
    return ResponseEntity.ok().body(service.listarVeiculos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    try {
      Veiculo veiculo = service.findById(id);
      if (veiculo != null)
        return ResponseEntity.ok().body(veiculo);
      else
        throw new RegraNegocioException("Veículo com ID " +
            id + " não encontrado");
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/ids")
  public ResponseEntity<List<?>> findVeiculosIds(@RequestParam List<Long> ids) {
    try {
      List<Veiculo> veiculos = service.findByIds(ids);
      if (veiculos.isEmpty()) {
        return ResponseEntity.notFound().build();
      } else {
        return ResponseEntity.ok(veiculos);
      }
    } catch (RegraNegocioException e) {
      throw new RegraNegocioException("Veículo com ID " + ids + " não encontrado.");
    }
  }

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody VeiculoDTO dto) {
    try {
      Veiculo veiculo = service.saveVeiculo(dto);
      return new ResponseEntity<>(veiculo, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody VeiculoDTO veiculoDTO) {
    try {
      Veiculo veiculo = service.findById(id);
      if (veiculo != null) {
        veiculo = service.atualizarVeiculo(id, veiculoDTO);
        return ResponseEntity.ok().body(veiculo);
      } else {
        throw new RegraNegocioException("Veículo com ID " + id + " não encontrado.");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    try {
      Veiculo veiculo = service.findById(id);
      if (veiculo != null) {
        service.deletarVeiculo(veiculo.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        throw new RegraNegocioException("Veículo com ID " + id + " não encontrado.");
      }
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
