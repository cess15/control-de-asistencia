package com.istb.app.services.profesor;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.istb.app.entities.Profesor;
import com.istb.app.models.DataTableResponse;

public interface ProfesorService {
	List<Profesor> findAll();

	Page<Profesor> findAll(Pageable pageable);
	
	DataTableResponse findAll(Integer draw, Integer start, Integer length, String search, Sort.Direction sort, String... properties) throws Exception;
	
	DataTableResponse findAllByInasistenciaGreaterThan(Integer draw, Integer start, Integer length, String search, Sort.Direction sort, String... properties) throws Exception;
	
	Profesor findById(int id);
	
	Profesor findByCedula(String cedula);

	Map<String, String> save(Profesor profesor);

	Map<String, String> update(Profesor profesor, int id);

	void delete(int id);
	
	String generateToken();

}
