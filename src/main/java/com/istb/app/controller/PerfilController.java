package com.istb.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.istb.app.entities.Periodo;
import com.istb.app.repository.PeriodoRepository;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.profesor.ProfesorService;

@Controller
public class PerfilController {

	@Autowired
	UserCredentials userCredentials;

	@Autowired
	ProfesorService profesorService;
	
	@Autowired
	PeriodoRepository periodoRepository;


	@GetMapping(value = "/perfil")
	public String inicio(Model model) {
		model.addAttribute("profesores", profesorService.findAll());
		model.addAttribute("title", "Panel ISTB - Perfil");
		model.addAttribute("user", userCredentials.getUserAuth());
		Periodo periodo = periodoRepository.findByVigente(true);
		if(periodo!=null) {
			model.addAttribute("periodo", periodo);
			model.addAttribute("fechaInicio",
					userCredentials.convertDate(periodo.getFechaInicio()));
			model.addAttribute("fechaFinal", userCredentials.convertDate(periodo.getFechaFinal()));			
		}
		return "dashboard/perfil";
	}
}
