package com.diesel_workshop_manager.diesel_workshop_manager.models.relatorio;

import java.util.Date;
import java.util.List;

import com.diesel_workshop_manager.diesel_workshop_manager.models.cliente.Cliente;
import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.Servico;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.Usuario;
import com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo.Veiculo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
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
  private List<Servico> servicos;

  @NotBlank
  private Date dataInicio;

  @NotBlank
  private Date dataFim;

}
