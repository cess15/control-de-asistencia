package com.istb.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.firebase.FirebaseService;
import com.istb.app.services.profesor.ProfesorServiceImpl;
import com.istb.app.services.user.UserServiceImpl;

@Controller
public class HomeController {

	@Autowired
	UserCredentials userCredentials;

	@Autowired
	FirebaseService fbmanager;

	@Autowired
	ProfesorServiceImpl profesorService;

	@Autowired
	UserServiceImpl userService;

	@GetMapping(value = "/inicio")
	public String inicio(Model model) {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());

		return "inicio";
	}

	@GetMapping(value = "/settings")
	public String settings(Model model) {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		return "dashboard/user/settings";
	}

}
