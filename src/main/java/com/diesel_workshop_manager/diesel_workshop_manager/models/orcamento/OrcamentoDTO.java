package com.diesel_workshop_manager.diesel_workshop_manager.models.orcamento;

import java.util.Date;
import java.util.List;

import com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio.RelatorioDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrcamentoDTO {
  private Long id;
  private List<RelatorioDTO> relatorioDTOs;
  private Date dataInicio;
  private Date dataFim;
  private Double subtotal;
  private Double desconto;
  private Double totalPrice;
}
