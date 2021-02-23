package com.istb.app.services.inasistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istb.app.entities.Inasistencia;
import com.istb.app.entities.Periodo;
import com.istb.app.repository.InasistenciaRepository;

@Service
public class InasistenciaServiceImpl implements InasistenciaService {
	
	@Autowired
	InasistenciaRepository inasistenciaRepository;
		
	@Transactional
	@Override
	public List<Inasistencia> save(Set<Inasistencia> inasistencias, Periodo periodo) {		
		List<Inasistencia> inasistenciasPeriodos = new ArrayList<Inasistencia>();
		for (Inasistencia inasistencia : inasistencias) {
			inasistencia.setFecha(LocalDate.now());
			inasistencia.setPeriodo(periodo);			
			inasistenciasPeriodos.add(inasistencia);
		}
		
		return inasistenciaRepository.saveAll(inasistenciasPeriodos);
	}

	@Transactional
	@Override
	public void update(Inasistencia inasistencia) {
		inasistenciaRepository.save(inasistencia);
	}

	@Override
	public Collection<Inasistencia> findByFechaActual(int profesor_id) {
		return inasistenciaRepository.findByProfesor_IdAndFecha(profesor_id, LocalDate.now());
	}

	@Override
	public Collection<Inasistencia> findByFechaActual() {
		return inasistenciaRepository.findByFecha(LocalDate.now());
	}
}
