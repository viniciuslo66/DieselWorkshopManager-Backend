package com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VeiculoDTO {
  private Long id;
  private String nomeVeiculo;
  private String prefixo;
  private Integer horimetro;
}
