package com.istb.app.api.controller.profesor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.istb.app.entities.Profesor;
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
	
	@GetMapping(value = "/profesores", headers = "Accept=Application/json")
	public List<Profesor> findAll () {
		List<Profesor> profesores = serviceProfesor.findAll()
			.stream()
			.peek(p -> p.setInasistencias(serviceInasistencia.findByFechaActual(p.getId())))
			.sorted(Comparator.comparing(Profesor::getApellidos))
			.collect(Collectors.toList());
				
		return profesores;
	}
}
