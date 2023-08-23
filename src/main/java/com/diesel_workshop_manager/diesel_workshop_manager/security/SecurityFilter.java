package com.diesel_workshop_manager.diesel_workshop_manager.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.diesel_workshop_manager.diesel_workshop_manager.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  TokenService tokenService;

  @Autowired
  UsuarioRepository repository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var token = this.recoverToken(request);

    if (token != null) {
      var login = tokenService.validateToken(token);
      UserDetails userDetails = repository.findByLogin(login);

      var authenticated = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticated);
    }

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {

    var authHeader = request.getHeader("Authorization");

    if (authHeader == null)
      return null;

    return authHeader.replace("Bearer ", " ");

  }

}
