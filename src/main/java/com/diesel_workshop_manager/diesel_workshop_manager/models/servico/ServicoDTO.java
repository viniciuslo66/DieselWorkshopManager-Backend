package com.diesel_workshop_manager.diesel_workshop_manager.models.servico;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServicoDTO {
  private Long id;
  private String nomeServico;
  private Double price;
}
