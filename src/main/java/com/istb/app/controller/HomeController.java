package com.istb.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.istb.app.entities.PeriodoProfesor;
import com.istb.app.repository.PeriodoProfesorRepository;
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

	@Autowired
	PeriodoProfesorRepository periodoProfesorRepository;

	@GetMapping(value = "/inicio")
	public String inicio(Model model) {
		PeriodoProfesor periodoProfesor = userCredentials.getPeriodoAndProfesor();
		model.addAttribute("profesores", profesorService.findAll());
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());

		if (periodoProfesor != null) {
			model.addAttribute("periodoProfesor", periodoProfesor);
			model.addAttribute("fechaInicio",
					userCredentials.convertDate(periodoProfesor.getPeriodo().getFechaInicio()));
			model.addAttribute("fechaFinal", userCredentials.convertDate(periodoProfesor.getPeriodo().getFechaFinal()));
		}

		return "inicio";
	}

	@GetMapping(value = "/periodo-profesores", produces = "application/json")
	public ResponseEntity<?> getProfesorPeriodoVigenteIsTrue() {
		return ResponseEntity.ok().body(periodoProfesorRepository.findByPeriodoVigenteIsTrue());
	}

	@PostMapping("/uploadfile")
	public ResponseEntity<?> uploadImage(MultipartFile[] file) throws Exception {

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(fbmanager.uploadFile(file[0]));

	}

}
