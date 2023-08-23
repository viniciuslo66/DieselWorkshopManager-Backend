package com.diesel_workshop_manager.diesel_workshop_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.AuthenticationDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.LoginResponseDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.RegisterDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.usuario.Usuario;
import com.diesel_workshop_manager.diesel_workshop_manager.repository.UsuarioRepository;
import com.diesel_workshop_manager.diesel_workshop_manager.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {

    var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((Usuario) auth.getPrincipal());

    return ResponseEntity.ok(new LoginResponseDTO(token));

  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {

    if (this.usuarioRepository.findByLogin(data.login()) != null)
      return ResponseEntity.badRequest().build();

    String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
    Usuario novoUsuario = new Usuario(data.nomeUsuario(), data.cpf(), data.email(), data.endereco(), data.telefone(),
        data.login(), encryptedPassword, data.role());

    this.usuarioRepository.save(novoUsuario);
    return ResponseEntity.ok().build();

  }
}
