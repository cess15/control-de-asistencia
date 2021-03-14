package com.istb.app.api.controller.motivos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.istb.app.entities.Motivo;
import com.istb.app.repository.MotivoRepository;

@RestController
@RequestMapping(value = "/api")
public class MotivoApiController {

	@Autowired
	MotivoRepository motiveRepository;

	@GetMapping(value = "/motivo/{id}", headers = "Accept=Application/json")
	public Optional<Motivo> getMotiveById(@PathVariable int id) {
		return motiveRepository.findById(id);
	}

}
