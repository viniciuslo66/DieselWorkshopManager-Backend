package com.diesel_workshop_manager.diesel_workshop_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diesel_workshop_manager.diesel_workshop_manager.repository.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {

  @Autowired
  UsuarioRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return repository.findByLogin(username);
  }

}
