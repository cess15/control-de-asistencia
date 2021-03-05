package com.istb.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {

	Profesor findByCedula(String cedula);

	Profesor findByCorreo(String correo);

	Optional<Profesor> findByCorreoAndIdIsNot(String correo, int id);

}
