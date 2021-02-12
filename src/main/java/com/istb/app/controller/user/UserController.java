package com.istb.app.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.istb.app.entities.Profesor;
import com.istb.app.entities.Role;
import com.istb.app.entities.Usuario;
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

	@GetMapping("/agregar-usuario")
	public String loadForm(Model model) {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("usuario", new Usuario());
		return "user/create";
	}

	@GetMapping(value = "/user")
	public ResponseEntity<?> findAll() throws Exception {
		return ResponseEntity.ok().body(userService.findAll());
	}

	@PostMapping(value = "/agregar-usuario")
	public String save(@Valid @ModelAttribute Usuario usuario, BindingResult result, RedirectAttributes redirectAttrs,
			Model model) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("title", "Panel ISTB");
			return "user/create";

		}
		Usuario existeNombreUsuario = userService.findByNombreUsuario(usuario.getNombreUsuario());
		Usuario existeProfesor = userService.findByProfesorCorreo(usuario.getProfesor().getCorreo());
		String password = userService.encodePassword(usuario.getContrasena());

		if (existeNombreUsuario != null && existeProfesor != null) {
			redirectAttrs.addFlashAttribute("nombreUsuario",
					"Usuario ".concat(usuario.getNombreUsuario()).concat(" ya existe"));
			redirectAttrs.addFlashAttribute("clase", "text-danger");
			redirectAttrs
					.addFlashAttribute("correo",
							"Usuario con este correo: ".concat(usuario.getProfesor().getCorreo()).concat(" ya existe"))
					.addFlashAttribute("clase", "text-danger");
			return "redirect:/agregar-usuario";

		} else if (existeProfesor != null) {
			redirectAttrs
					.addFlashAttribute("correo",
							"Usuario con este correo: ".concat(usuario.getProfesor().getCorreo()).concat(" ya existe"))
					.addFlashAttribute("clase", "text-danger");
			return "redirect:/agregar-usuario";

		} else if (existeNombreUsuario != null) {
			redirectAttrs.addFlashAttribute("nombreUsuario",
					"Usuario ".concat(usuario.getNombreUsuario()).concat(" ya existe"));
			redirectAttrs.addFlashAttribute("clase", "text-danger");
			return "redirect:/agregar-usuario";

		}
		Profesor profesor = usuario.getProfesor();
		Usuario user = new Usuario();
		Role rol = rolService.findByNombre("Docente");
		user.setId(usuario.getId());
		user.setEstado(false);
		user.setNombreUsuario(usuario.getNombreUsuario());
		user.setContrasena(password);
		rol.addUsuario(user);
		user.addRol(rol);
		profesor.setId(usuario.getProfesor().getId());
		profesor.setUsuario(user);
		userService.save(user);
		profesorService.save(profesor);

		redirectAttrs.addFlashAttribute("mensaje", "Usuario registrado correctamente").addFlashAttribute("clase",
				"success");
		return "redirect:/agregar-usuario";
	}

}
