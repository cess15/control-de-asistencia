package com.istb.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping(value = { "/", "", "index" })
	public String index(Model model) {
		model.addAttribute("title", "ISTB | Control de Asistencias");
		return "index";
	}

	@GetMapping(value = "/about")
	public String about(Model model) {
		model.addAttribute("title", "ISTB | Acerca de Nosotros");
		return "about";
	}

	@GetMapping(value = "/cycv")
	public String cycv(Model model) {
		model.addAttribute("title", "ISTB | Curriculum Vitaem");
		return "cycv";
	}

	@GetMapping(value = "/contact")
	public String contact(Model model) {
		model.addAttribute("title", "ISTB | Contacto");
		return "contact";
	}

}
