package com.istb.app.api.controller.inasistencia;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.istb.app.entities.Inasistencia;
import com.istb.app.entities.Periodo;
import com.istb.app.models.ErrorResponse;
import com.istb.app.services.inasistencia.InasistenciaService;
import com.istb.app.services.periodo.PeriodoService;

@RestController
@RequestMapping(value = "/api")
public class InasistenciaApiController {
	
	@Autowired
	InasistenciaService serviceInasistencia;
	
	@Autowired
	PeriodoService servicePeriodo;
	
	@GetMapping(value = "/inasistencias", headers = "Accept=Application/json")
	public Collection<Inasistencia> index () {
		return serviceInasistencia.findByFechaActual();
	}
	
	@GetMapping(value = "/inasistencias/{id}/profesor", headers = "Accept=Application/json")
	public Collection<Inasistencia> getInasistenciasByProfesor (@PathVariable(name = "id") int profesor_id) {
		return serviceInasistencia.findByFechaActual(profesor_id);
	}
	
	@PostMapping(value = "/inasistencias", headers = "Accept=Application/json")
	public ResponseEntity<?> save (@RequestBody Set<Inasistencia> inasistencias) {
		Collection<Inasistencia> assistWasTaken  = serviceInasistencia.findByFechaActual();
		
		if (assistWasTaken.size() > 0) {
			return new ResponseEntity<>(
				new ErrorResponse("Ya se tomó asistencia"), HttpStatus.BAD_REQUEST);	
		}
		
		if (inasistencias.size() == 0) {
			return new ResponseEntity<>(
				new ErrorResponse("Debe seleccionar por lo menos un docente que haya faltado."), HttpStatus.BAD_REQUEST);	
		}
		
		Periodo periodo = servicePeriodo.findPeriodoVigente();
		
		if (periodo == null) {
			return new ResponseEntity<>(
				new ErrorResponse("No hay un periodo vigente."), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(
				this.serviceInasistencia.save(inasistencias, periodo), HttpStatus.CREATED);	
	}
	
}
