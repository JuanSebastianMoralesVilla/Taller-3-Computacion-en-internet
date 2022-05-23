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

import com.taller1SM.controller.interfaces.ProductInventoryController;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.service.LocationService;
import com.taller1SM.service.ProductService;
import com.taller1SM.service.ProductServiceImp;
import com.taller1SM.service.ProductcostHistoricImp;
import com.taller1SM.service.ProductinventoryImp;
import com.taller1SM.service.ProductinventoryService;

@Controller
public class ProductInventoryControllerImp implements ProductInventoryController {

	// relaciones de product inventory
	private ProductinventoryService productinventoryService;
	private LocationService locationService;
	private ProductService productservice;

	public ProductInventoryControllerImp(ProductinventoryService productInventoryService, ProductService productservice,
			LocationService locationService) {

		this.productinventoryService = productInventoryService;
		this.productservice = productservice;
		this.locationService = locationService;
	}

	@GetMapping("/templatesProductInventory/")
	public String indexpir(Model model) {

		model.addAttribute("productinventory", productinventoryService.findAll());
		return "templatesProductInventory/Index";

	}

	@GetMapping("/templatesProductInventory/add-productInventory")
	public String addpir(Model model) {

		model.addAttribute("productinventory", new Productinventory());
		model.addAttribute("products", productservice.findAll());
		model.addAttribute("locations", locationService.findAll());

		return "templatesProductInventory/add-productInventory";

	}

	@PostMapping("/templatesProductInventory/add-productInventory/")
	public String saveInventory(@Validated @ModelAttribute Productinventory productinventory,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {

		if (action.equals("Cancel")) {
			return "redirect:/templatesProductInventory/";
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("products", productservice.findAll());
			model.addAttribute("locations", locationService.findAll());
			return "/templatesProductInventory/add-productInventory";
		}

		if (!action.equals("Cancel")) {
			productinventoryService.savePIR(productinventory, productinventory.getProduct().getProductid(),
					productinventory.getLocation().getLocationid());
		}

		return "redirect:/templatesProductInventory/";
	}

	@GetMapping("/templatesProductInventory/edit/{id}")
	public String showUpdateInventory(@PathVariable("id") Integer id, Model model) {
		Optional<Productinventory> productinventory = productinventoryService.findById(id);
		if (productinventory == null)
			throw new IllegalArgumentException("Invalid  Id:" + id);
		model.addAttribute("productinventory", productinventory.get());
		model.addAttribute("products", productservice.findAll());
		model.addAttribute("locations", locationService.findAll());
		return "templatesProductInventory/update-productInventory";
	}

	@PostMapping("/templatesProductInventory/edit/{id}")
	public String updateInventory(@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action,
			@Validated @ModelAttribute Productinventory productinventory, BindingResult bindingResult, Model model) {
		if (action.equals("Cancel"))
			return "redirect:/templatesProductInventory/";
		if (bindingResult.hasErrors()) {
			model.addAttribute("products", productservice.findAll());
			model.addAttribute("locations", locationService.findAll());
			productinventory.setId(id);
			return "templatesProductInventory/update-productInventory";
		}
		if (!action.equals("Cancel")) {
			productinventoryService.editPIR(id, productinventory, productinventory.getProduct().getProductid(),
					productinventory.getLocation().getLocationid());
			model.addAttribute("products", productservice.findAll());
		}
		return "redirect:/templatesProductInventory/";
	}
	
	
	@GetMapping("/templatesProduct2/Index/{id}")
	public String showProducttoPIR(@PathVariable("id") Integer id, Model model) {
		
		Productinventory pir= productinventoryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
       
		model.addAttribute("products", pir.getProduct());
		return "templatesproduct/Index";
	}
	
	@GetMapping("/templatesLocation/Index/{id}")
	public String showLocationtoPIR(@PathVariable("id") Integer id, Model model) {
		
		Productinventory pir= productinventoryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + id));
       
		model.addAttribute("locations", pir.getLocation());
		return "templatesLocation/Index";
	}


}
