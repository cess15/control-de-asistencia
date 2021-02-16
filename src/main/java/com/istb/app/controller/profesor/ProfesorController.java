package com.istb.app.controller.profesor;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.istb.app.entities.PeriodoProfesor;
import com.istb.app.entities.Profesor;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.profesor.ProfesorServiceImpl;
import com.istb.app.services.rol.RolServiceImpl;
import com.istb.app.services.user.UserServiceImpl;

@Controller
public class ProfesorController {

	@Autowired
	UserCredentials userCredentials;

	@Autowired
	ProfesorServiceImpl profesorService;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	RolServiceImpl rolService;

	@GetMapping(value = "/editar-profesor/{id}")
	public String loadForm(@PathVariable("id") int id, @ModelAttribute("profesor") Profesor profesor, Model model) {

		model.addAttribute("profesor", profesorService.findById(id));

		return "user/edit";

	}

	@GetMapping(value = "/user")
	public ResponseEntity<?> findAll() throws Exception {
		return ResponseEntity.ok().body(userCredentials.getPeriodoAndProfesor());
	}

	@PostMapping(value = "/editar-profesor/{id}")
	public String actualizarProfesor(@PathVariable("id") int id, @Valid @ModelAttribute("profesor") Profesor profesor,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) throws Exception {

		if (result.hasErrors()) {
			model.addAttribute("profesor", profesor);
			return "user/edit";
		}

		Map<String, String> errors = profesorService.update(profesor, id);

		if (errors.isEmpty()) {

			errors.put("clase", "success");
			errors.put("mensaje", "Profesor registrado correctamente");
			redirectAttrs.addFlashAttribute("errors", errors);

		} else {
			errors.put("clase", "danger");
			errors.put("mensaje", "El profesor no pudo ser registrado!");
			redirectAttrs.addFlashAttribute("errors", errors);

		}
		return "redirect:/editar-profesor/" + id;
	}

	@GetMapping(value = "/eliminar-profesor/{id}")
	public String delete(@PathVariable("id") int id, Model model) throws Exception {
		model.addAttribute("profesor", profesorService.findById(id));

		profesorService.delete(id);

		return "redirect:/inicio";

	}

	@GetMapping(value = "/editar")
	public String loadFormUpdateProfile(Model model) {
		PeriodoProfesor periodoProfesor = userCredentials.getPeriodoAndProfesor();
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", periodoProfesor);
		return "profile/editarperfil";
	}

	@PostMapping(value = "/editar/perfil")
	public String actualizarPerfil(@Valid @ModelAttribute("user") PeriodoProfesor periodoProfesor,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) throws Exception {
		
		if (result.hasErrors()) {
			return "profile/editarperfil";
		}
		
		int id = userCredentials.getPeriodoAndProfesor().getProfesor().getId();
		Map<String, String> errors = profesorService.update(periodoProfesor.getProfesor(), id);

		if (errors.isEmpty()) {

			errors.put("clase", "success");
			errors.put("mensaje", "Profesor registrado correctamente");
			redirectAttrs.addFlashAttribute("errors", errors);

		} else {
			errors.put("clase", "danger");
			errors.put("mensaje", "El profesor no pudo ser registrado!");
			redirectAttrs.addFlashAttribute("errors", errors);

		}
		return "redirect:/inicio";
	}

	@ModelAttribute
	public void controllerGloabalAttributes(Model attributes) {

		attributes.addAttribute("title", "Panel ISTB");

	}

}
