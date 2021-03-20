package com.istb.app.api.controller.user;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.istb.app.entities.Usuario;
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

}
