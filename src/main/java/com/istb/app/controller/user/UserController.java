package com.istb.app.controller.user;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.istb.app.entities.Periodo;
import com.istb.app.entities.Profesor;
import com.istb.app.repository.PeriodoRepository;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.profesor.ProfesorServiceImpl;
import com.istb.app.services.rol.RolServiceImpl;
import com.istb.app.services.user.UserServiceImpl;

@Controller
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	ProfesorServiceImpl profesorService;

	@Autowired
	RolServiceImpl rolService;

	@Autowired
	PeriodoRepository periodoRepository;
	
	@Autowired
	UserCredentials userCredentials;
	
	@GetMapping("/agregar-usuario")
	public String loadForm(Model model) {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("profesor", new Profesor());
		Periodo periodo = periodoRepository.findByVigente(true);
		if(periodo!=null) {
			model.addAttribute("periodo", periodo);
			model.addAttribute("fechaInicio",
					userCredentials.convertDate(periodo.getFechaInicio()));
			model.addAttribute("fechaFinal", userCredentials.convertDate(periodo.getFechaFinal()));			
		}
		return "user/create";

	}

	@PostMapping(value = "/agregar-usuario")
	public String addProfesor(@Valid @ModelAttribute Profesor profesor, BindingResult result,
			RedirectAttributes redirectAttrs, Model model) {

		if (result.hasErrors()) {

			model.addAttribute("title", "Panel ISTB");
			System.out.println(profesor);

			return "user/create";

		}

		Map<String, String> errors = profesorService.save(profesor);

		if (errors.isEmpty()) {

			errors.put("clase", "success");
			errors.put("mensaje", "Profesor registrado correctamente");
			redirectAttrs.addFlashAttribute("errors", errors);

		} else {
			errors.put("clase", "danger");
			errors.put("mensaje", "El profesor no pudo ser registrado!");
			redirectAttrs.addFlashAttribute("errors", errors);

		}

		redirectAttrs.addFlashAttribute("oldValues", profesor);
		return "redirect:/agregar-usuario";

	}

	@ModelAttribute
	public void controllerGloabalAttributes(Model attributes) {

		attributes.addAttribute("title", "Panel ISTB");

	}

}
