package com.diesel_workshop_manager.diesel_workshop_manager.models.usuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {
  private Long id;
  private String nomeUsuario;
  private String cpf;
  private String email;
  private Long endereco;
  private Integer telefone;
  private String login;
  private String senha;
}
