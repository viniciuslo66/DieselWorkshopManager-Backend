package com.diesel_workshop_manager.diesel_workshop_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diesel_workshop_manager.diesel_workshop_manager.models.veiculo.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByIdIn(List<Long> ids);
}
