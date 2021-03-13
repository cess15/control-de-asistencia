package com.istb.app.services.inasistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istb.app.entities.Inasistencia;
import com.istb.app.entities.Periodo;
import com.istb.app.entities.Profesor;
import com.istb.app.repository.InasistenciaRepository;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.periodo.PeriodoService;

@Service
public class InasistenciaServiceImpl implements InasistenciaService {
	
	@Autowired
	InasistenciaRepository inasistenciaRepository;
	
	@Autowired
	PeriodoService servicePeriodo;
	
	@Autowired
	UserCredentials userCredentials;
	
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

	@Override
	public List<Inasistencia> findByInjustificado() {
		Profesor profesor = userCredentials.getUserAuth().getProfesor();
		return this.inasistenciaRepository
			.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrue(false, false, profesor.getId());
	}
	
	@Override
	public List<Inasistencia> findByJustificado() {
		Profesor profesor = userCredentials.getUserAuth().getProfesor();
		return this.inasistenciaRepository
				.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrue(true, false, profesor.getId());
	}

	@Override
	public Inasistencia findByUltimoInjustificado() {
		Profesor profesor = userCredentials.getUserAuth().getProfesor();
		Periodo periodo = servicePeriodo.findPeriodoVigente();
		return this.inasistenciaRepository
			.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrueLast(false, false, profesor.getId(), periodo.getId(), LocalDate.now());
	}
	
	@Override
	public Inasistencia findByUltimoJustificado() {
		Profesor profesor = userCredentials.getUserAuth().getProfesor();
		Periodo periodo = servicePeriodo.findPeriodoVigente();
		return this.inasistenciaRepository
			.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrueLast(true, false, profesor.getId(), periodo.getId(), LocalDate.now());
	}

	@Override
	public Inasistencia findByProfesor(int profesor_id) {
		Periodo periodo = servicePeriodo.findPeriodoVigente();
		return this.inasistenciaRepository
			.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrueLast(false, false, profesor_id, periodo.getId(), LocalDate.now());
	}
	
	@Override
	public Inasistencia findByProfesorJutified(int profesor_id) {
		Periodo periodo = servicePeriodo.findPeriodoVigente();
		return this.inasistenciaRepository
			.findByJustificacionDigitalAndJustificacionFisicaAndProfesor_IdAndPeriodo_VigenteIsTrueLast(true, false, profesor_id, periodo.getId(), LocalDate.now());
	}

	@Override
	public Inasistencia findById(int id) {
		
		Optional<Inasistencia> inasistencia = inasistenciaRepository.findById(id);
		if (inasistencia.isPresent()) {
			return inasistencia.get();
		}
		return null;
	}
}
