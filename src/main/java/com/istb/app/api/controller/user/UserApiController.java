package com.istb.app.api.controller.user;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.istb.app.entities.Usuario;
import com.istb.app.models.ErrorResponse;
import com.istb.app.models.FileUpload;
import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.firebase.FirebaseService;
import com.istb.app.services.user.UserService;

@RestController
@RequestMapping(value = "/api")
public class UserApiController {

	@Autowired
	UserCredentials serviceCredentials;

	@Autowired
	FirebaseService fbmanager;

	@Autowired
	UserService userService;

	@GetMapping(value = "/user/auth", headers = "Accept=Application/json")
	public ResponseEntity<?> index() {
		return ResponseEntity.status(HttpStatus.OK).body(this.serviceCredentials.getUserAuth());
	}

	@PostMapping(value = "/profile/avatar", headers = "Accept=Application/json")
	public ResponseEntity<?> updateAvatar(MultipartFile[] file) throws Exception {
		Usuario usuario = serviceCredentials.getUserAuth();
		FileUpload fileUpload = fbmanager.uploadFile(file[0]);
		usuario.setUrl_imagen_perfil(fileUpload.getUrl());
		fbmanager.deleteFile(usuario.getImagenPerfil());
		usuario.setImagenPerfil(fileUpload.getName());
		userService.update(usuario);
		File upload = new File(file[0].getOriginalFilename());
		fbmanager.deleteFile(upload.getPath(), upload.getName());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(fileUpload);
	}

	@PostMapping(value = "/user/updatePassword", headers = "Accept=Application/json")
	public ResponseEntity<?> changeUserPassword(@RequestParam("newPassword") String newPassword,
			@RequestParam("oldPassword") String oldPassword) {

		Usuario usuario = serviceCredentials.getUserAuth();
		if (userService.checkIfValidOldPassword(usuario, oldPassword) != false) {
			userService.changeUserPassword(usuario, newPassword);
			return new ResponseEntity<>(new ErrorResponse("Contraseña cambiada correctamente"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorResponse("Verifique si la contraseña que ingreso es la misma"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/user/updateUserName", headers = "Accept=Application/json")
	public ResponseEntity<?> changeUserName(@RequestParam("userName") String userName,
			@RequestParam("oldPasswordUserName") String oldPasswordUserName, Authentication auth) {

		Usuario usuario = serviceCredentials.getUserAuth();
		if (userService.checkIfValidOldPassword(usuario, oldPasswordUserName) != false) {
			Usuario userFound = userService.findByNombreUsuario(userName);
			if (userFound != null) {
				return new ResponseEntity<>(new ErrorResponse("El nombre de usuario ya existe"),
						HttpStatus.BAD_REQUEST);
			}
			usuario.setNombreUsuario(userName);
			userService.update(usuario);
			auth.setAuthenticated(false);
			return new ResponseEntity<>(new ErrorResponse("Nombre de usuario cambiado correctamente, por favor vuelva a iniciar sesión"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorResponse("Verifique si la contraseña que ingreso es la misma"),
					HttpStatus.BAD_REQUEST);
		}
	}

}
