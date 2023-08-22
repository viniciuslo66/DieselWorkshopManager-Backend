package com.diesel_workshop_manager.diesel_workshop_manager.models.endereco;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoDTO {
  private Long id;
  private String estado;
  private String bairro;
  private String nua;
  private int numero;
  private int cep;
}
