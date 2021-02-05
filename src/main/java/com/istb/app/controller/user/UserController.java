package com.istb.app.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/create-user")
	public String loadForm(Model model) {
		model.addAttribute("title", "Panel ISTB");
		return "user/create";
	}
}
