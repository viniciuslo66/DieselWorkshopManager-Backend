package com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio;

import java.util.Date;
import java.util.Map;

import com.diesel_workshop_manager.diesel_workshop_manager.models.cliente.Cliente;
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.Servico;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.Usuario;
import com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo.Veiculo;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Relatorios")
public class Relatorio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Cliente cliente;

  @ManyToOne
  private Usuario usuario;

  @NotNull
  @ManyToOne
  private Veiculo veiculo;

  @ElementCollection
  @CollectionTable(name = "Relatorio_Servicos", joinColumns = @JoinColumn(name = "relatorio_id"))
  @MapKeyJoinColumn(name = "servico_id")
  @Column(name = "quantidade")
  private Map<Servico, Integer> servicos;

  @NotNull
  private Date dataInicio;

  @NotNull
  private Date dataFim;

}
