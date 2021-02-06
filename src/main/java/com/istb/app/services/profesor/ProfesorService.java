package com.istb.app.services.profesor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.istb.app.entities.Profesor;

public interface ProfesorService {
	List<Profesor> findAll();

	Page<Profesor> findAll(Pageable pageable);

	Profesor findById(int id);

	Profesor findByCedula(String cedula);

	void update(Profesor profesor);

	void delete(int id);

}
