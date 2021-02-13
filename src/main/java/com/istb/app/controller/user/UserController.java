package com.istb.app.controller.user;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.istb.app.entities.Profesor;
import com.istb.app.entities.Usuario;
import com.istb.app.services.profesor.ProfesorServiceImpl;
import com.istb.app.services.rol.RolServiceImpl;
import com.istb.app.services.user.UserServiceImpl;

@Controller
public class UserController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserServiceImpl userService;

	@Autowired
	ProfesorServiceImpl profesorService;

	@Autowired
	RolServiceImpl rolService;

	@GetMapping("/agregar-usuario")
	public String loadForm(Model model) {
		
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("profesor", new Profesor());

		return "user/create";
	
	}

	@GetMapping(value = "/user")
	public ResponseEntity<?> findAll() throws Exception {
		return ResponseEntity.ok().body(userService.findAll());
	}

	@PostMapping(value = "/agregar-usuario")
	public String addProfesor(@Valid @ModelAttribute Profesor usuario, BindingResult result, 
		RedirectAttributes redirectAttrs, Model model) {
		
		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				log.info(error.getField());
			}
			
			model.addAttribute("title", "Panel ISTB");
			return "user/create";
		}
		
		log.info(usuario.toString());
		
		Map<String, String> errors = profesorService.save(usuario);
		
		if( errors.isEmpty() ) {

			errors.put("clase", "success");
			errors.put("mensaje", "Profesor registrado correctamente");
		
		} else {
			errors.put("mensaje", "El profesor no pudo ser registrado!"); }

		redirectAttrs.addFlashAttribute("errors", errors);
		
		return "redirect:/agregar-usuario";

	}

}
