package com.taller1SM.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taller1SM.controller.interfaces.LocationController;
import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.service.LocationService;
import com.taller1SM.service.LocationServiceImp;

@Controller
public class LocationControllerImp implements LocationController{

	
	LocationService locationService;
	
	
	
	
	@Autowired
	public LocationControllerImp(LocationService locationService) {
		
		this.locationService = locationService;
	}


	@GetMapping("/templatesLocation/")
	public String indexLocation(Model model) {
		model.addAttribute("locations", locationService.findAll());
		return "templatesLocation/Index";
	}
	
	@GetMapping("/templatesLocation/add-location")
	public String listProduct(Model model) {
		model.addAttribute("location", new Location());
		return "templatesLocation/add-location";
	}

	
	
	
	@PostMapping("/templatesLocation/add-location/")
	public String saveLocation(@Validated @ModelAttribute Location location,
			BindingResult bindingResult, Model model,@RequestParam(value = "action",
			required = true) String action) {
		
		if (action.equals("Cancel"))
			return "redirect:/templatesLocation/";

		if (bindingResult.hasErrors()) {
		
			return "templatesLocation/add-location";
		}
		if (!action.equals("Cancel")) {	
			locationService.saveLocation(location);
		}
		System.out.println("EXITO add locations");
		return "redirect:/templatesLocation/";
	}
	
	@GetMapping("/templatesLocation/edit/{id}")
	public String showUpdateLocation(@PathVariable("id") Integer id, Model model) {
	
		Optional<Location> location = locationService.findById(id);
		if (location == null)
			throw new IllegalArgumentException("Invalid location Id:" + id);
		model.addAttribute("location", location.get());

		return "templatesLocation/update-location";
	}

	@PostMapping("/templatesLocation/edit/{id}")
	public String updateLocation(
			@PathVariable("id") Integer id, 
			@RequestParam(value = "action", required = true) String action,
			@Validated @ModelAttribute Location location, 
			BindingResult bindingResult,
			Model model
			) {
		if (action.equals("Cancel"))
			return "redirect:/templatesLocation/";
		if (bindingResult.hasErrors()) {
			location.setLocationid(id);
			return "templatesLocation/update-location";
		}
		if (!action.equals("Cancel")) {
			locationService.editLocation(id, location);
			model.addAttribute("locations", locationService.findAll());
		}
		return "redirect:/templatesLocation/";
	}
	
	
	

	
	@GetMapping("/templatesProductInventory/Index/{id}")
	public String showLocation(@PathVariable("id") Integer id, Model model) {
		
		Location l= locationService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid location:" + id));
       
		model.addAttribute("productinventory", l.getProductinventories());
		return "templatesProductInventory/Index";
	}

}
