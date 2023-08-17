package com.diesel_workshop_manager.diesel_workshop_manager.models.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "Orcamentos")
public class Orcamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @OneToMany
  private Relatorio[] relatorios;

  @NotBlank
  private Date dataInicio;

  @NotBlank
  private Date dataFim;

  @NotNull
  private Double subtotal;

  @NotNull
  private Double desconto;

  @NotNull
  private Double totalPrice;
}
