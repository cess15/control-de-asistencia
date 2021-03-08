package com.istb.app.controller.profesor;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		model.addAttribute("user", userCredentials.getUserAuth());

		return "user/edit";

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
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		model.addAttribute("docente", userCredentials.getUserAuth().getProfesor());
		return "profile/editarperfil";
	}

	@PostMapping(value = "/editar/perfil")
	public String actualizarPerfil(@Valid @ModelAttribute("docente") Profesor profesor, BindingResult result, Model model,
			RedirectAttributes redirectAttrs) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("user", userCredentials.getUserAuth());
			model.addAttribute("docente", userCredentials.getUserAuth().getProfesor());
			System.out.println(profesor);
			return "profile/editarperfil";
		}

		Map<String, String> errors = profesorService.update(profesor,
				userCredentials.getUserAuth().getProfesor().getId());

		if (errors.isEmpty()) {

			errors.put("clase", "success");
			errors.put("mensaje", "Perfil actualizado correctamente");
			redirectAttrs.addFlashAttribute("errors", errors);

		} else {
			errors.put("clase", "danger");
			errors.put("mensaje", "Ocurrio un problema al actualizar su perfil!");
			redirectAttrs.addFlashAttribute("errors", errors);

		}
		return "redirect:/editar";
	}

	@ModelAttribute
	public void controllerGloabalAttributes(Model attributes) {

		attributes.addAttribute("title", "Panel ISTB");

	}

}
