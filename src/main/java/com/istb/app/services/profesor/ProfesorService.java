package com.istb.app.services.profesor;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.istb.app.entities.Profesor;

public interface ProfesorService {
	List<Profesor> findAll();

	Page<Profesor> findAll(Pageable pageable);

	Profesor findById(int id);

	Profesor findByCedula(String cedula);
	
	Profesor findByCorreo(String correo);

	Map<String, String> save(Profesor profesor);

	void update(Profesor profesor, int id);

	void delete(int id);

}
