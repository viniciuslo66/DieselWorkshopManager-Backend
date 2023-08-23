package com.diesel_workshop_manager.diesel_workshop_manager.models.usuario;

import java.util.Collection;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Usuarios")
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  private String nomeUsuario;

  @CPF
  private String cpf;

  @Email
  private String email;

  @NotBlank
  @Embedded
  @OneToOne
  private Endereco endereco;

  @NotBlank
  private Integer telefone;

  @NotBlank
  @Column(unique = true)
  private String login;

  @NotBlank
  private String senha;

  @NotBlank
  private UsuarioRole role;

  public Usuario(@NotBlank String nomeUsuario, @CPF String cpf, @Email String email, @NotBlank Endereco endereco,
      @NotBlank Integer telefone, @NotBlank String login, @NotBlank String senha, @NotBlank UsuarioRole role) {
    this.nomeUsuario = nomeUsuario;
    this.cpf = cpf;
    this.email = email;
    this.endereco = endereco;
    this.telefone = telefone;
    this.login = login;
    this.senha = senha;
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.role == UsuarioRole.ADMIN)
      return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    else
      return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return getSenha();
  }

  @Override
  public String getUsername() {
    return getLogin();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
