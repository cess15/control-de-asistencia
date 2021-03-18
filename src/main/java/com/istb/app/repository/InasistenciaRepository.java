package com.istb.app.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Inasistencia;

@Repository
public interface InasistenciaRepository extends JpaRepository<Inasistencia, Integer> {

	List<Inasistencia> findByProfesor_IdAndFecha(int profesor_id, LocalDate fecha);

	@Query("SELECT i from Inasistencia i WHERE i.fecha = ?1  AND i.justificacionDigital=true AND i.justificacionFisica=false")
	Page<Inasistencia> findAll(LocalDate date, Pageable pageable);
	
	@Query("SELECT i from Inasistencia i WHERE i.justificacionDigital=true AND i.justificacionFisica=false")
	Page<Inasistencia> findAll(Pageable pageable);

	@Query("SELECT COUNT(i) FROM Inasistencia i WHERE i.fecha = ?1 AND i.justificacionDigital=true AND i.justificacionFisica=false")
	long countInasistencia(LocalDate date);

	Collection<Inasistencia> findByFecha(LocalDate fecha);

	@Query(value = "SELECT i FROM Inasistencia i WHERE i.justificacionDigital = ?1 AND i.justificacionFisica = ?2 AND i.profesor.id = ?3 AND i.periodo.vigente = true ORDER BY i.id desc")
	List<Inasistencia> findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrue(
			Boolean justificacionDigital, Boolean justificacionFisica, int profesor_id);

	@Query(value = "SELECT i FROM Inasistencia i WHERE i.justificacionDigital = ?1 AND i.justificacionFisica = ?2 AND i.periodo.vigente = true ORDER BY i.id desc")
	List<Inasistencia> findByJustificacionDigitalAndJustificacionFisicaAndPeriodo_VigenteIsTrue(
			Boolean justificacionDigital, Boolean justificacionFisica);

	@Query(value = "SELECT * FROM inasistencias WHERE justificacion_digital = ?1 AND justificacion_fisica = ?2 AND profesor_id = ?3 AND periodo_id = ?4 AND fecha = ?5 ORDER BY id desc LIMIT 1", nativeQuery = true)
	Inasistencia findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrueLast(
			Boolean justificacionDigital, Boolean justificacionFisica, int profesor_id, int periodo_id,
			LocalDate fecha);
}
