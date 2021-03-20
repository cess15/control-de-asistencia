package com.istb.app.api.controller.permisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.istb.app.models.DataTableResponse;
import com.istb.app.services.permiso.PermisoService;

@RestController
@RequestMapping(value = "/api")
public class PermisoApiController {

	@Autowired
	PermisoService permisoService;

	@GetMapping(value = "/ver-permisos/{profesorId}")
	public ResponseEntity<?> dataProfesores(@PathVariable int profesorId,
			@RequestParam(required = false, defaultValue = "1") Integer draw,
			@RequestParam(required = false, defaultValue = "0") Integer start,
			@RequestParam(required = false, defaultValue = "2") Integer length) throws Exception {
		DataTableResponse dataTableResp = permisoService
				.findAllByInasistencia_Profesor_IdAndInasistencia_Periodo_VigenteIsTrue(profesorId, draw, start, length,
						Sort.Direction.DESC, "fechaGeneracion");

		return ResponseEntity.status(HttpStatus.OK).body(dataTableResp);
	}

}
