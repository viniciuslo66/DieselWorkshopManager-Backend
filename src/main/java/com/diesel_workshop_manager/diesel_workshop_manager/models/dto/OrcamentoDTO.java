package com.diesel_workshop_manager.diesel_workshop_manager.models.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrcamentoDTO {
  private Long id;
  private RelatorioDTO[] relatorioDTOs;
  private Date dataInicio;
  private Date dataFim;
  private Double subtotal;
  private Double desconto;
  private Double totalPrice;
}
