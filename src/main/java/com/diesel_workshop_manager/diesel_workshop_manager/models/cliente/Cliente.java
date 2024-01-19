package com.diesel_workshop_manager.diesel_workshop_manager.models.cliente;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.Endereco;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Clientes")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String nomeCliente;

  @CPF
  private String cpf;

  @CNPJ
  private String CNPJ;

  @Email
  @NotBlank
  private String email;

  @OneToOne
  @Embedded
  private Endereco endereco;

  @NotBlank
  private String telefone;
}
