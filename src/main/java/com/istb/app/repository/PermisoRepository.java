package com.istb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
	
	@Query(value = "SELECT p FROM Permiso p WHERE p.inasistencia.id = ?1 AND p.inasistencia.profesor.id = ?2 AND p.inasistencia.periodo.vigente = true")
	Permiso findByInasistencia_IdAndInasistencia_Periodo_VigenteIsTrue(int inasistenciaId, int profesorId);
	
}
