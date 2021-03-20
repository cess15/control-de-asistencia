package com.istb.app.api.controller.adjunto;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.istb.app.entities.Adjunto;
import com.istb.app.entities.Permiso;
import com.istb.app.models.FileUpload;
import com.istb.app.repository.AdjuntoRepository;
import com.istb.app.services.firebase.FirebaseService;
import com.istb.app.services.permiso.PermisoService;

@RestController
@RequestMapping(value = "/api")
public class AdjuntoApiController {

	@Autowired
	FirebaseService fbmanager;

	@Autowired
	AdjuntoRepository adjuntoRepository;

	@Autowired
	PermisoService permisoService;

	@PostMapping(value = "/adjuntar-permiso/{permisoId}")
	public ResponseEntity<?> upFile(@PathVariable int permisoId, MultipartFile[] file) throws Exception {
		Adjunto adjunto = new Adjunto();
		Permiso permiso = permisoService.findById(permisoId);
		FileUpload fileUpload = fbmanager.uploadFile(file[0]);
		adjunto.setPermiso(permiso);
		adjunto.setUrlAdjunto(fileUpload.getUrl());
		adjunto.setNombreAdjunto(fileUpload.getName());
		this.adjuntoRepository.save(adjunto);
		File upload = new File(file[0].getOriginalFilename());
		fbmanager.deleteFile(upload.getPath(), upload.getName());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(fileUpload);
	}
}
