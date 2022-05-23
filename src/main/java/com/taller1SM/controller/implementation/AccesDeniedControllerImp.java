package com.taller1SM.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.taller1SM.controller.interfaces.AccessDeniedController;


@Controller
public class AccesDeniedControllerImp implements AccessDeniedController {

	@Autowired
	public AccesDeniedControllerImp() {
	}

	@GetMapping("/accesDenied")
	public String accesDenied(Model model) {
		return "/accesDenied";
	}
}
