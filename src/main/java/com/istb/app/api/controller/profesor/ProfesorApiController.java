package com.istb.app.api.controller.profesor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.istb.app.entities.Profesor;
import com.istb.app.models.DataTableResponse;
import com.istb.app.repository.ProfesorRepository;
import com.istb.app.services.inasistencia.InasistenciaService;
import com.istb.app.services.profesor.ProfesorService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(methods = RequestMethod.GET)
public class ProfesorApiController {

	@Autowired
	InasistenciaService serviceInasistencia;

	ProfesorService serviceProfesor;

	public ProfesorApiController(ProfesorService serviceProfe) {
		serviceProfesor = serviceProfe;
	}

	@Autowired
	ProfesorRepository profesorRepository;

	@GetMapping(value = "/profesores", headers = "Accept=Application/json")
	public List<Profesor> findAll() {
		List<Profesor> profesores = profesorRepository.findAllByUsuario_EstadoIsTrue().stream()
				.peek(p -> p.setInasistencias(serviceInasistencia.findByFechaActual(p.getId())))
				.sorted(Comparator.comparing(Profesor::getApellidos)).collect(Collectors.toList());

		return profesores;
	}

	@GetMapping(value = "/data-profesores")
	public ResponseEntity<?> dataProfesores(@RequestParam(required = false, defaultValue = "1") Integer draw,
			@RequestParam(required = false, defaultValue = "0") Integer start,
			@RequestParam(required = false, defaultValue = "2") Integer length,
			@RequestParam(required = false) Map<String, String> search) throws Exception {
		DataTableResponse dataTableResp = serviceProfesor.findAll(draw, start, length, search.get("search[value]"),
				Sort.Direction.DESC, "cedula");

		return ResponseEntity.status(HttpStatus.OK).body(dataTableResp);
	}

	@GetMapping(value = "/data-profesores-inasistencia")
	public ResponseEntity<?> dataProfesoresInasistence(@RequestParam(required = false, defaultValue = "1") Integer draw,
			@RequestParam(required = false, defaultValue = "0") Integer start,
			@RequestParam(required = false, defaultValue = "2") Integer length,
			@RequestParam(required = false) Map<String, String> search) throws Exception {
		DataTableResponse dataTableResp = serviceProfesor.findAllByInasistenciaGreaterThan(draw, start, length,
				search.get("search[value]"), Sort.Direction.DESC, "cedula");

		return ResponseEntity.status(HttpStatus.OK).body(dataTableResp);
	}
}
