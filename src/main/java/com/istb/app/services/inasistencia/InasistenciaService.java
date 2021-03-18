package com.istb.app.services.inasistencia;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;

import com.istb.app.entities.Inasistencia;
import com.istb.app.entities.Periodo;
import com.istb.app.models.DataTableResponse;

public interface InasistenciaService {
	
	List<Inasistencia> save(Set <Inasistencia> inasistencia, Periodo periodo);
	
	Inasistencia findByProfesor(int profesor_id);
	
	Inasistencia findByProfesorJutified(int profesor_id);
	
	void update(Inasistencia inasistencia);
	
	List<Inasistencia> findByFechaActual (int id);
	
	DataTableResponse findAll(Integer draw, Integer start, Integer length, String search, Sort.Direction sort, String... properties) throws Exception;
	
	void sendEmailByInjustified(List<Inasistencia> inasistencias);
	
	void sendEmailByJustified(List<Inasistencia> inasistencias);
	
	Collection<Inasistencia> findByFechaActual ();
	
	List<Inasistencia> findByInjustificado();
	
	List<Inasistencia> findByJustificado();
	
	Inasistencia findByUltimoInjustificado();
	
	Inasistencia findByUltimoJustificado();

	Inasistencia findById(int id);
}
