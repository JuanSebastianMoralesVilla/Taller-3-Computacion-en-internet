package com.taller1SM.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taller1SM.controller.interfaces.ProductController;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.service.LocationServiceImp;
import com.taller1SM.service.ProductService;
import com.taller1SM.service.ProductServiceImp;

@Controller

public class ProductControllerImp implements ProductController {

	private ProductService productService;

	@Autowired
	public ProductControllerImp(ProductService productService) {

		this.productService = productService;
	}

	@GetMapping("/templatesProduct/")
	public String indexProduct(Model model) {
		model.addAttribute("products", productService.findAll());
		System.out.println("ENTRE A LA PAGINA");
		return "tempLatesProduct/Index";
	}

	@GetMapping("/templatesProduct/add-product")
	public String listProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("subcategories", productService.findAllSubcategory());
		System.out.println("subcategorias agregadas");

		return "templatesProduct/add-product";
	}

	@PostMapping("/templatesProduct/add-product/")
	public String saveProduct(@Validated @ModelAttribute Product product, BindingResult bindingResult, Model model,
			@RequestParam(value = "action", required = true) String action) {

		if (!action.equals("Cancel")) {
			model.addAttribute("subcategories", productService.findAllSubcategory());
			if (bindingResult.hasErrors()) {
				return "templatesProduct/add-product";
			} else if (product.getSellenddate().isBefore(product.getSellstartdate())) {
				model.addAttribute("dateError", true);
				return "templatesProduct/add-product";
			}
			productService.saveProduct(product, null, product.getProductsubcategory().getProductsubcategoryid());
		}
		return "redirect:/templatesProduct/";
	}
	
	@GetMapping("/templatesProduct/edit/{id}")
	public String showUpdateProduct(@PathVariable("id") Integer id, Model model) {
		Optional<Product> product = productService.findById(id);
		if (product == null)
			throw new IllegalArgumentException("invalide Id:" + id);
		model.addAttribute("product", product.get());
		model.addAttribute("subcategories", productService.findAllSubcategory());
		return "templatesProduct/update-product";
	}

	@PostMapping("/templatesProduct/edit/{id}")
	public String updateProduct(
			@PathVariable("id") Integer id, 
			@RequestParam(value = "action", required = true) String action,
			@Validated @ModelAttribute Product product, 
			BindingResult bindingResult,
			Model model
			) {
		if (action.equals("Cancel"))
			return "redirect:/templatesProduct/";
		if (product.getSellstartdate() != null && product.getSellenddate() != null) {
			if (product.getSellstartdate().isAfter(product.getSellenddate())) {
				bindingResult.addError(
						// mensaje de error mediante campo de error mismo nombre en el html
						new FieldError("product", "sellstartdate", "La fecha de inicio de venta debe ser menor a la fecha final de venta."));
			}
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("subcategories", productService.findAllSubcategory());
			product.setProductid(id);
			return "templatesProduct/update-product";
		}
		if (!action.equals("Cancel")) {
			productService.editProduct(id, product, null, product.getProductsubcategory().getProductsubcategoryid());
			model.addAttribute("products", productService.findAll());
		}
		return "redirect:/templatesProduct/";
	}
	
	
	
	
	// consultas producto tiene asociado una categoria
	
	
	@GetMapping("/templatesproduct/consultasubcategory/{id}")
	public String showProducts(@PathVariable("id") Integer id, Model model) {
		
		//Product p = productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
		model.addAttribute("products", productService.findAll());
		model.addAttribute("subcategories", productService.findAllSubcategory());
		return "templatesproduct/consultasubcategory";
	}
	
	
	
	@GetMapping("/templatesProductCostHistoric/Index/{id}")
	public String showProductsCostHistoric(@PathVariable("id") Integer id, Model model) {
		
		Product p= productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product cost historic Id:" + id));
       
		model.addAttribute("productcosthistory", p.getProductcosthistories());
		return "templatesProductCostHistoric/Index";
	}

	

	@GetMapping("/templatesProduct/Index/{id}")
	public String showProductsInventory(@PathVariable("id") Integer id, Model model) {
		
		Product p= productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Inventory id " + id));
       
		model.addAttribute("productinventory", p.getProductinventories());
		return "templatesProductInventory/Index";
	}
}

