package com.diesel_workshop_manager.diesel_workshop_manager.models.usuario;

import org.hibernate.validator.constraints.br.CPF;

import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.Endereco;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
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
@Entity(name = "Usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String nomeUsuario;

  @CPF
  @NotNull
  private String cpf;

  @Email
  private String email;

  @Embedded
  @OneToOne
  private Endereco endereco;

  @NotNull
  private Integer telefone;

  @NotBlank
  @Column(unique = true)
  private String login;

  @NotBlank
  private String senha;
}
