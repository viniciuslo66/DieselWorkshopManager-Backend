package com.diesel_workshop_manager.diesel_workshop_manager.models.orcamento;

import java.util.Date;
import java.util.List;

import com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio.Relatorio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Orcamentos")
public class Orcamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @OneToMany
  private List<Relatorio> relatorios;

  private Date dataInicio;

  private Date dataFim;

  private Double subtotal;

  private Double desconto;

  private Double totalPrice;
}
