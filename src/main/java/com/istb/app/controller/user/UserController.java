package com.istb.app.controller.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		return "user/create";
	}

	@GetMapping(value = "/user")
	public ResponseEntity<?> findAll() throws Exception {
		return ResponseEntity.ok().body(userService.findAll());
	}

	@PostMapping(value = "/agregar-usuario")
	public String save(@ModelAttribute Usuario usuario, Profesor profesor, RedirectAttributes redirectAttrs)
			throws Exception {
		Usuario newUser = userService.findByNombreUsuario(usuario.getNombreUsuario());
		Usuario nuevoProfesor = userService.findByProfesorCorreo(profesor.getCorreo());
		Role rol = rolService.findByNombre("Docente");
		Collection<Role> roles = new ArrayList<>();
		Collection<Usuario> usuarios = new ArrayList<>();

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);
		String password = passwordEncoder.encode(usuario.getContrasena());

		if (newUser != null) {
			redirectAttrs
					.addFlashAttribute("mensaje", "Usuario ".concat(usuario.getNombreUsuario()).concat(" ya existe"))
					.addFlashAttribute("clase", "danger");
			return "redirect:/agregar-usuario";
		}
		if (nuevoProfesor != null) {
			redirectAttrs
					.addFlashAttribute("mensaje",
							"Usuario con este correo: ".concat(profesor.getCorreo()).concat(" ya existe"))
					.addFlashAttribute("clase", "danger");
			return "redirect:/agregar-usuario";
		}
		roles.add(rol);
		usuarios.add(usuario);

		usuario.setRoles(roles);
		usuario.setEstado(false);
		usuario.setContrasena(password);
		rol.setUsuarios(usuarios);
		userService.save(usuario);

		profesor.setUsuario(usuario);
		profesorService.save(profesor);

		redirectAttrs.addFlashAttribute("mensaje", "Usuario registrado correctamente").addFlashAttribute("clase",
				"success");
		return "redirect:/agregar-usuario";
	}

}
