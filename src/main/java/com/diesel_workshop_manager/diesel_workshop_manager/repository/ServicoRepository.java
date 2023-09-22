package com.diesel_workshop_manager.diesel_workshop_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diesel_workshop_manager.diesel_workshop_manager.models.servico.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByIdIn(List<Long> ids);
}
