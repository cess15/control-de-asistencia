package com.istb.app.controller.profesor;

import java.util.List;

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
import com.istb.app.entities.Usuario;
import com.istb.app.services.profesor.ProfesorServiceImpl;
import com.istb.app.services.rol.RolServiceImpl;
import com.istb.app.services.user.UserServiceImpl;

@Controller
public class ProfesorController {

	@Autowired
	ProfesorServiceImpl profesorService;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	RolServiceImpl rolService;

	@GetMapping(value = "/editar-profesor/{id}")
	public String loadForm(@PathVariable("id") int id, @ModelAttribute("profesor") Profesor profesor, Model model) {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("profesor", profesorService.findById(id));
		return "user/edit";
	}

	@PostMapping(value = "/editar-profesor/{id}")
	public String save(@PathVariable("id") int id, @Valid @ModelAttribute("profesor") Profesor profesor,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) throws Exception {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("profesor", profesor);
		if (result.hasErrors()) {
			model.addAttribute("title", "Panel ISTB");
			return "user/edit";
		}
		profesorService.save(profesor);
		return "redirect:/inicio";
	}

	@GetMapping(value = "/eliminar-profesor/{id}")
	public String delete(@PathVariable("id") int id, Model model) throws Exception {
		model.addAttribute("profesor", profesorService.findById(id));

		// Role rol = rolService.findByNombre("Docente");
		// rol.removeUsuario(user);
		// user.removeRol(rol);
		// userService.delete(user.getId());
		// profesorService.delete(profesor.getId());

		// System.out.println(user);
		// System.out.println(p);
		// System.out.println(rol);
		/*
		 * rol.removeUsuario(user); user.removeRol(rol);
		 * userService.delete(user.getId()); profesorService.delete(profesor.getId());
		 */
		return "redirect:/inicio";
	}

}
