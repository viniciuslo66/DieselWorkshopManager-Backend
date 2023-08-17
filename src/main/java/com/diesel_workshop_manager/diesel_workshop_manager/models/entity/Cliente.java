package com.diesel_workshop_manager.diesel_workshop_manager.models.entity;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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

  @NotBlank
  @OneToOne
  @Embedded
  private Endereco endereco;

  @NotBlank
  private String telefone;
}
