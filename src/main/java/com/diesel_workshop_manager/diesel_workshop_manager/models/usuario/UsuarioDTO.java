package com.diesel_workshop_manager.diesel_workshop_manager.models.usuario;

import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.EnderecoDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {
  private Long id;
  private String nomeUsuario;
  private String cpf;
  private String email;
  private EnderecoDTO enderecoDTO;
  private Integer telefone;
  private String login;
  private String senha;
}
