package com.istb.app.controller.periodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.istb.app.services.auth.UserCredentials;

@Controller
public class PeriodoController {
	
	@Autowired
	UserCredentials userCredentials;
	
	@GetMapping(value = "/periodos")
	public String loadView(Model model) {
		model.addAttribute("title","Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		return "dashboard/periodos";
	}
	
}
