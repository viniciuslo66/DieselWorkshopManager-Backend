package com.diesel_workshop_manager.diesel_workshop_manager.models.usuario;

import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.Endereco;

public record RegisterDTO(String nomeUsuario, String cpf, String email, Endereco endereco, Integer telefone,
    String login, String senha, UsuarioRole role) {
}
