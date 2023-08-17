package com.diesel_workshop_manager.diesel_workshop_manager.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diesel_workshop_manager.diesel_workshop_manager.models.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}
