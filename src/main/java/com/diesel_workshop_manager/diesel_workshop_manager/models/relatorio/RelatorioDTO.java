package com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio;

import java.util.Date;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatorioDTO {
  private Long id;
  private Long cliente;
  private Long usuario;
  private Long veiculo;
  private Map<Long, Integer> servicos;
  private Date dataInicio;
  private Date dataFim;
}
