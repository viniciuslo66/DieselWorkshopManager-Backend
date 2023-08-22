package com.diesel_workshop_manager.diesel_workshop_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diesel_workshop_manager.diesel_workshop_manager.models.cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
