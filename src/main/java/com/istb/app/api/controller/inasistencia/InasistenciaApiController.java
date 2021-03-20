package com.istb.app.api.controller.inasistencia;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.istb.app.entities.Inasistencia;
import com.istb.app.entities.Periodo;
import com.istb.app.entities.Profesor;
import com.istb.app.models.DataTableResponse;
import com.istb.app.models.ErrorResponse;
import com.istb.app.repository.InasistenciaRepository;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.inasistencia.InasistenciaService;
import com.istb.app.services.periodo.PeriodoService;

@RestController
@RequestMapping(value = "/api")
public class InasistenciaApiController {

	@Autowired
	InasistenciaService serviceInasistencia;

	@Autowired
	PeriodoService servicePeriodo;

	@Autowired
	UserCredentials serviceCredentials;

	@Autowired
	InasistenciaRepository inasistenciaRepository;

	@GetMapping(value = "/inasistencias", headers = "Accept=Application/json")
	public Collection<Inasistencia> index() {
		return serviceInasistencia.findByFechaActual();
	}

	@GetMapping(value = "/inasistencias/unjustified", headers = "Accept=Application/json")
	public List<Inasistencia> getInasistenciasNoJustificada() {
		return this.serviceInasistencia.findByInjustificado();
	}

	@GetMapping(value = "/inasistencias/justified", headers = "Accept=Application/json")
	public List<Inasistencia> getInasistenciasJustificada() {
		return this.serviceInasistencia.findByJustificado();
	}

	@GetMapping(value = "/inasistencias/{id}/profesor", headers = "Accept=Application/json")
	public Collection<Inasistencia> getInasistenciasByProfesor(@PathVariable(name = "id") int profesor_id) {
		return serviceInasistencia.findByFechaActual(profesor_id);
	}

	@GetMapping(value = "/inasistencias/last", headers = "Accept=Application/json")
	public Inasistencia getLastInasistencias() {
		Profesor profesor = this.serviceCredentials.getUserAuth().getProfesor();
		return serviceInasistencia.findByProfesor(profesor.getId());
	}

	@GetMapping(value = "/inasistenciasJustified/last", headers = "Accept=Application/json")
	public Inasistencia getLastInasistenciasJustified() {
		Profesor profesor = this.serviceCredentials.getUserAuth().getProfesor();
		return serviceInasistencia.findByProfesorJutified(profesor.getId());
	}

	@PostMapping(value = "/inasistencias", headers = "Accept=Application/json")
	public ResponseEntity<?> save(@RequestBody Set<Inasistencia> inasistencias) {
		Collection<Inasistencia> assistWasTaken = serviceInasistencia.findByFechaActual();

		if (assistWasTaken.size() > 0) {
			return new ResponseEntity<>(new ErrorResponse("Ya se tomó asistencia"), HttpStatus.BAD_REQUEST);
		}

		if (inasistencias.size() == 0) {
			return new ResponseEntity<>(new ErrorResponse("Debe seleccionar por lo menos un docente que haya faltado."),
					HttpStatus.BAD_REQUEST);
		}

		Periodo periodo = servicePeriodo.findPeriodoVigente();

		if (periodo == null) {
			return new ResponseEntity<>(new ErrorResponse("No hay un periodo vigente."), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(this.serviceInasistencia.save(inasistencias, periodo), HttpStatus.CREATED);
	}

	@GetMapping(value = "/cant-inasistencias/{inasistenciaId}")
	public long cantInasistencias(@PathVariable int inasistenciaId) {
		return this.inasistenciaRepository.countInasistenciaByProfesor(inasistenciaId);
	}

	@GetMapping(value = "/inasistenciasJustified")
	public ResponseEntity<?> index(@RequestParam(required = false, defaultValue = "1") Integer draw,
			@RequestParam(required = false, defaultValue = "0") Integer start,
			@RequestParam(required = false, defaultValue = "2") Integer length,
			@RequestParam(required = false) Map<String, String> search) throws Exception {

		DataTableResponse dataTableResp = serviceInasistencia.findAll(draw, start, length, search.get("search[value]"),
				Sort.Direction.DESC, "fecha");

		return ResponseEntity.status(HttpStatus.OK).body(dataTableResp);
	}

}
