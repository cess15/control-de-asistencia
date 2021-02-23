package com.istb.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Periodo;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Integer> {
	
	Periodo findByVigente(boolean vigente);
	
	@Query("SELECT p from Periodo p WHERE year(p.fechaInicio) = ?1 OR year(p.fechaFinal) = ?1")
	Page<Periodo> findAll (Integer anio, Pageable pageable);
	
	@Query("SELECT p from Periodo p WHERE year(p.fechaInicio) = ?1 AND month(p.fechaInicio) = ?2 OR year(p.fechaFinal) = ?1 AND month(p.fechaFinal) = ?2")
	Page<Periodo> findAll (Integer anio, Integer mes, Pageable pageable);
	
	@Query("SELECT p from Periodo p WHERE year(p.fechaInicio) = ?1 AND month(p.fechaInicio) = ?2 AND day(p.fechaInicio) = ?3 OR year(p.fechaFinal) = ?1 AND month(p.fechaFinal) = ?2 AND day(p.fechaFinal) = ?3")
	Page<Periodo> findAll (Integer anio, Integer mes, Integer dia, Pageable pageable);
	
	@Query("SELECT COUNT(p) FROM Periodo p WHERE year(p.fechaInicio) = ?1 OR year(p.fechaFinal) = ?1")
	long countPeriodo (Integer anio);
	
	@Query("SELECT COUNT(p) FROM Periodo p WHERE year(p.fechaInicio) = ?1 AND month(p.fechaInicio) = ?2 OR year(p.fechaFinal) = ?1 AND month(p.fechaFinal) = ?2")
	long countPeriodo (Integer anio, Integer mes);
	
	@Query("SELECT COUNT(p) FROM Periodo p WHERE year(p.fechaInicio) = ?1 AND month(p.fechaInicio) = ?2 AND day(p.fechaInicio) = ?3 OR year(p.fechaFinal) = ?1 AND month(p.fechaFinal) = ?2 AND day(p.fechaFinal) = ?3")
	long countPeriodo (Integer anio, Integer mes, Integer dia);
}
