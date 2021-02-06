package com.istb.app.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.istb.app.services.auth.UserCredentials;
import com.istb.app.services.firebase.FirebaseService;

@Controller
public class HomeController {

	@Autowired
	UserCredentials userCredentials;
	
	@Autowired
	FirebaseService fbmanager;
	
	@GetMapping(value = "/inicio")
	public String inicio(Model model) {
		model.addAttribute("title", "Panel ISTB");
		model.addAttribute("user", userCredentials.getUserAuth());
		return "inicio";
	}

	@PostMapping("/uploadfile")
	public ResponseEntity<?> uploadImage(MultipartFile[] file) throws Exception {

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(fbmanager.uploadFile(file[0]));

	}
	
}