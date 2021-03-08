package com.istb.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.istb.app.entities.Profesor;
import com.istb.app.entities.Usuario;
import com.istb.app.services.profesor.ProfesorService;
import com.istb.app.services.user.UserService;

@Controller
public class VerificationController {

	@Autowired
	ProfesorService profesorService;

	@Autowired
	UserService userService;

	@GetMapping("/verify/{id}/{token}")
	public String verify(@PathVariable int id, @PathVariable String token) {
		Profesor profesor = profesorService.findById(id);
		if (profesor.getTokenVerification().equals(token)) {
			Usuario usuario = profesor.getUsuario();
			usuario.setEstado(true);
			userService.update(usuario);
			return "redirect:/login";
		}
		return "redirect:/login";
	}

}
