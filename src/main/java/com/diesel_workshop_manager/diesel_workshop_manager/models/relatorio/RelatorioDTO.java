package com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio;

import java.util.Date;
import java.util.Map;

import com.diesel_workshop_manager.diesel_workshop_manager.models.cliente.ClienteDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.ServicoDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.UsuarioDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo.VeiculoDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatorioDTO {
  private Long id;
  private ClienteDTO clienteDTO;
  private UsuarioDTO usuarioDTO;
  private VeiculoDTO veiculoDTO;
  private Map<ServicoDTO, Integer> servicoDTOs;
  private Date dataInicio;
  private Date dataFim;
}
