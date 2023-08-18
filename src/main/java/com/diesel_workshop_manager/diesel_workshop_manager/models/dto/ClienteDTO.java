package com.diesel_workshop_manager.diesel_workshop_manager.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDTO {
  private Long id;
  private String nomeCliente;
  private String cpf;
  private String cnpj;
  private String email;
  private EnderecoDTO enderecoDTO;
  private String telefone;
}
