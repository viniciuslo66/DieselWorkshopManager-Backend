package com.diesel_workshop_manager.diesel_workshop_manager.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diesel_workshop_manager.diesel_workshop_manager.models.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
