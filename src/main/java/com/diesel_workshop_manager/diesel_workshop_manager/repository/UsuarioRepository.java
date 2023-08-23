package com.diesel_workshop_manager.diesel_workshop_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  public UserDetails findByLogin(String login);

}
