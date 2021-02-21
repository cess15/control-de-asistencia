package com.istb.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.istb.app.services.user.UserService;

@SpringBootApplication
public class BaseApplication {
	
	@Autowired
	UserService serviceUser;
	
	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}

}