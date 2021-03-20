package com.istb.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {

	@Query("SELECT p from Profesor p WHERE p.usuario.estado=true")
	Page<Profesor> findAll(Pageable pageable);

	@Query("SELECT p from Profesor p WHERE p.cedula = ?1 AND p.usuario.estado=true")
	Page<Profesor> findAll(String cedula, Pageable pageable);
	
	@Query("SELECT COUNT(p) FROM Profesor p WHERE p.cedula = ?1 AND p.usuario.estado=true")
	long countProfesor(String cedula);
	
	@Query(value = "select * from profesores p inner join inasistencias i on i.profesor_id=p.id", nativeQuery = true)
	Page<Profesor> findAllByInasistencias(Pageable pageable);
	
	@Query(value = "select * from profesores p inner join inasistencias i on i.profesor_id=p.id where p.cedula=?", nativeQuery = true)
	Page<Profesor> findAllByInasistencias(String cedula, Pageable pageable);
	
	@Query(value = "select count(*) from profesores p inner join inasistencias i on i.profesor_id=p.id", nativeQuery = true)
	long countProfesorByInasistencia();
	
	@Query(value = "select count(*) from profesores p inner join inasistencias i on i.profesor_id=p.id where p.cedula=?", nativeQuery = true)
	long countProfesorByInasistenciaAndCedula(String cedula);
	
	Profesor findByCedula(String cedula);

	Profesor findByCorreo(String correo);

	Optional<Profesor> findByCorreoAndIdIsNot(String correo, int id);
}
