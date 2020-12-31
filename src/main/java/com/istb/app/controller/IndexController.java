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

}
