package com.istb.app.controller.asistencia;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InasistenciaController {
		
	@GetMapping(value = "/justificar-inasistencia/{id}", produces="text/plain")
	public String index(@PathVariable int id, Model model) {
		System.out.println("Justificando inasistencia " + id);
		return "redirect:/inicio";
	}
}
