package com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "Veiculos")
public class Veiculo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String nomeVeiculo;

  @NotBlank
  private String prefixo;

  @NotBlank
  private Integer horimetro;
}
