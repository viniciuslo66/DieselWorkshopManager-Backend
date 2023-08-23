package com.diesel_workshop_manager.diesel_workshop_manager.models.usuario;

public enum UsuarioRole {
  ADMIN("admin"),
  USER("user");

  private String role;

  UsuarioRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }

}
