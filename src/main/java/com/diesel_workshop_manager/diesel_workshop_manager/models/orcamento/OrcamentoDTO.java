package com.diesel_workshop_manager.diesel_workshop_manager.models.orcamento;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrcamentoDTO {
  private Long id;
  private List<Long> relatorios;
}
