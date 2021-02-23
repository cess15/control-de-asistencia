package com.istb.app.api.controller.periodo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.istb.app.models.DataTableResponse;
import com.istb.app.services.periodo.PeriodoService;

@RestController
@RequestMapping(value = "/api")
public class PeriodoApiController {
	
	@Autowired
	PeriodoService servicePeriodo;
	
	@GetMapping(value = "/periodos")
	public ResponseEntity<?> index (@RequestParam(required = false, defaultValue = "1") Integer draw, 
			@RequestParam(required = false, defaultValue = "0") Integer start, 
			@RequestParam(required = false, defaultValue = "2") Integer length,
			@RequestParam(required = false) Map<String, String> search) throws Exception { 
				
		DataTableResponse dataTableResp = servicePeriodo
				.findAll(draw, start, length, search.get("search[value]"), Sort.Direction.DESC, "fechaFinal");
		
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(dataTableResp);
	}
}
