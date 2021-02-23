package com.istb.app.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Inasistencia;

@Repository
public interface InasistenciaRepository extends JpaRepository<Inasistencia, Integer> {
	
	Collection<Inasistencia> findByProfesor_IdAndFecha (int profesor_id, LocalDate fecha);
	
	Collection<Inasistencia> findByFecha (LocalDate fecha);
}
