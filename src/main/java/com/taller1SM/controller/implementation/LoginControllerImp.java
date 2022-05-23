package com.taller1SM.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.taller1SM.controller.interfaces.LoginController;
@Controller
public class LoginControllerImp implements LoginController{
	
	@Autowired
	public LoginControllerImp() {
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "/login";
	}

}
