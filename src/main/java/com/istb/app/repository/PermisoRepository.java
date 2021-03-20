package com.istb.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

	@Query(value = "SELECT p FROM Permiso p WHERE p.inasistencia.id = ?1 AND p.inasistencia.periodo.vigente = true")
	Permiso findByInasistencia_IdAndInasistencia_Periodo_VigenteIsTrue(int inasistenciaId);

	@Query("SELECT p from Permiso p WHERE p.inasistencia.profesor.id =?1 AND p.inasistencia.periodo.vigente = true")
	Page<Permiso> findAllByInasistencia_Profesor_IdAndInasistencia_Periodo_VigenteIsTrue(int profesorId,
			Pageable pageable);

	@Query("SELECT p from Permiso p WHERE p.inasistencia.profesor.id =?1 AND p.inasistencia.periodo.vigente = true")
	Permiso findByInasistencia_Profesor_IdAndInasistencia_Periodo_VigenteIsTrue(int profesorId);

}
