package com.istb.app.controller.asistencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.istb.app.services.auth.UserCredentials;

@Controller
public class AsistenciaController {

	@Autowired
	UserCredentials userCredentials;

	@GetMapping(value = "/asistencias")
	public String loadTemplate(Model model) {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		return "dashboard/asistencias";
	}
	
	@GetMapping(value = "/no-access")
	public String noAccess(@RequestParam int start, @RequestParam int end, Model model) {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		return "dashboard/no-access";
	}
}
