package com.istb.app.services.inasistencia;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.istb.app.entities.Inasistencia;
import com.istb.app.entities.Periodo;

public interface InasistenciaService {
	
	List<Inasistencia> save(Set <Inasistencia> inasistencia, Periodo periodo);
	
	Inasistencia findByProfesor(int profesor_id);
	
	Inasistencia findByProfesorJutified(int profesor_id);
	
	void update(Inasistencia inasistencia);
	
	List<Inasistencia> findByFechaActual (int id);
	
	void sendEmailByInjustified(List<Inasistencia> inasistencias);
	
	void sendEmailByJustified(List<Inasistencia> inasistencias);
	
	Collection<Inasistencia> findByFechaActual ();
	
	List<Inasistencia> findByInjustificado();
	
	List<Inasistencia> findByJustificado();
	
	Inasistencia findByUltimoInjustificado();
	
	Inasistencia findByUltimoJustificado();

	Inasistencia findById(int id);
}
