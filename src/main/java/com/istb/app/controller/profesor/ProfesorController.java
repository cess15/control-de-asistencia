package com.istb.app.controller.profesor;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.istb.app.services.profesor.ProfesorServiceImpl;
import com.istb.app.services.rol.RolServiceImpl;
import com.istb.app.services.user.UserServiceImpl;

@Controller
public class ProfesorController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProfesorServiceImpl profesorService;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	RolServiceImpl rolService;

	@GetMapping(value = "/editar-profesor/{id}")
	public String loadForm(
		@PathVariable("id") int id, 
		@ModelAttribute("profesor") Profesor profesor, Model model) {
		
		// model.addAttribute("profesor", profesorService.findById(id));
	
		return "user/edit";
	
	}

	@PostMapping(value = "/editar-profesor/{id}")
	public String save(@PathVariable("id") int id, @Valid @ModelAttribute("profesor") Profesor profesor,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) throws Exception {
		
		log.info(profesor.toString());
		
		if (result.hasErrors()) {
			model.addAttribute("profesor", profesor);
			return "user/edit";
		}
		
		profesorService.update(profesor, id);
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

	@ModelAttribute
	public void controllerGloabalAttributes(Model attributes) {

		attributes.addAttribute("title", "Panel ISTB");

	}

}
