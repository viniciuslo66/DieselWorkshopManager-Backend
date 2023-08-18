package com.diesel_workshop_manager.diesel_workshop_manager.models.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatorioDTO {
  private Long id;
  private ClienteDTO clienteDTO;
  private UsuarioDTO usuarioDTO;
  private VeiculoDTO veiculoDTO;
  private ServicoDTO[] servicoDTOs;
  private Date dataInicio;
  private Date dataFim;
}
