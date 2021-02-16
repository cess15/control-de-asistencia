package com.istb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Periodo;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Integer> {
	Periodo findByVigente(boolean vigente);
}
