package com.diesel_workshop_manager.diesel_workshop_manager.models.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "Relatorios")
public class Relatorio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @OneToOne
  private Cliente cliente;

  @NotNull
  @OneToOne
  private Usuario usuario;

  @NotNull
  @OneToOne
  private Veiculo veiculo;

  @NotNull
  @OneToMany
  private Servico[] servicos;

  @NotBlank
  private Date dataInicio;

  @NotBlank
  private Date dataFim;

}
