package com.istb.app.api.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.istb.app.services.auth.UserCredentials;

@RestController
@RequestMapping(value = "/api")
public class UserApiController {
	
	@Autowired
	UserCredentials serviceCredentials;
	
	@GetMapping(value = "/user/auth", headers = "Accept=Application/json")
	public ResponseEntity<?> index () {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(this.serviceCredentials.getUserAuth());
	}
}
