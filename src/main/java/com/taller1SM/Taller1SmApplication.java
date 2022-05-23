package com.taller1SM;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.model.prod.Productsubcategory;
import com.taller1SM.model.user.User;
import com.taller1SM.model.user.UserType;
import com.taller1SM.repositories.LocationRepository;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;
import com.taller1SM.repositories.ProductcosthistoryRepository;
import com.taller1SM.repositories.ProductinventoryRepository;
import com.taller1SM.repositories.UserRepository;
import com.taller1SM.service.ProductServiceImp;



@ComponentScan(basePackages={"com.taller1SM"})
@SpringBootApplication

public class Taller1SmApplication {

	
	
	@Bean
	public  Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
		
		
	
	public static void main(String[] args) {
		SpringApplication.run(Taller1SmApplication.class, args);
	}
	
	
	

	@Bean
	public CommandLineRunner runner( ProductRepository productRepository, 
			ProductSubcategoryRepository subcategoryRepo, ProductcategoryRepository categoryRepo,
			LocationRepository locationRepository, ProductcosthistoryRepository costRepo, 
			ProductinventoryRepository inventoryRepo, UserRepository userRepository) {
		
		return (args) -> {
			
			
		//----------usuarios----------------
			//  administrador gestiona productos y locations
			// user: admin contraseña : 1234
			// user: operador contraseña 1234
			
			
			User userAdmin = new User();
			userAdmin.setId(1193029087);
			userAdmin.setName("Administrador 1");
			userAdmin.setUsername("admin");
			userAdmin.setPassword("{noop}1234");
			userAdmin.setType(UserType.admin);
			userRepository.save(userAdmin);
			
			
			System.out.println("Cargamos los datos de admin");
			
			User userop = new User();
			userAdmin.setId(123456789);
			userAdmin.setName("Operador 1");
			userAdmin.setUsername("operador");
			userAdmin.setPassword("{noop}1234");
			userAdmin.setType(UserType.operador);
			userRepository.save(userAdmin);
		
			System.out.println("Cargamos los datos de operador");
	
			Productcategory category = new Productcategory();
			category.setName("PRODUCTOS TECNOLOGIA");
			category.setProductsubcategories(new ArrayList<Productsubcategory>());
			
			Productsubcategory subcategory = new Productsubcategory();
			subcategory.setName("COMPUTADOR");
			subcategoryRepo.save(subcategory);
			category.addProductsubcategory(subcategory);
			
			subcategory = new Productsubcategory();
			subcategory.setName("CONSOLAS");
			subcategoryRepo.save(subcategory);
			category.addProductsubcategory(subcategory);
			
			subcategory = new Productsubcategory();
			subcategory.setName("TELEFONOS");
			subcategoryRepo.save(subcategory);
			category.addProductsubcategory(subcategory);
			
			
			Product p = new Product();
			p.setProductnumber("03");
			p.setName("MACBOOK PRO 14");
			p.setSellstartdate(LocalDate.of(2022, 5, 20));
			p.setSellenddate(LocalDate.of(2022, 5, 28));
			p.setWeight(20);		
			p.setSize(3);
			p.setProductsubcategory(subcategoryRepo.findById(1).get());
			productRepository.save(p);
			
			p = new Product();
			p.setProductnumber("08");
			p.setName("PS 4");
			p.setSellstartdate(LocalDate.of(2022, 3, 28));
			p.setSellenddate(LocalDate.of(2022, 4, 2));
			p.setWeight(200);		
			p.setSize(5);
			p.setProductsubcategory(subcategoryRepo.findById(2).get());
			productRepository.save(p);
			
		
			p = new Product();
			p.setProductnumber("05");
			p.setName("IPHONE");
			p.setSellstartdate(LocalDate.of(2022, 2, 7));
			p.setSellenddate(LocalDate.of(2022, 2, 8));
			p.setWeight(200);		
			p.setSize(10);
			p.setProductsubcategory(subcategoryRepo.findById(3).get());
			productRepository.save(p);
			
			Location locationN = new Location();
			locationN.setName("Cali-7001");
			locationN.setAvailability(new BigDecimal(5));
			locationN.setCostrate(new BigDecimal(0));
			
			locationRepository.save(locationN);
			
			locationN = new Location();
			locationN.setName("BOG-5001");
			locationN.setAvailability(new BigDecimal(8));
			locationN.setCostrate(new BigDecimal(1));
			locationRepository.save(locationN);
			
			
			
		

			Productcosthistory pHistoric = new Productcosthistory();
			pHistoric.setModifieddate(LocalDate.of(2022, 5, 20));
			pHistoric.setEnddate(LocalDate.of(2022, 5, 28));
			pHistoric.setProduct(p);
			pHistoric.setStandardcost(new BigDecimal(80000));
			costRepo.save(pHistoric);
			
			
			pHistoric = new Productcosthistory();
			pHistoric.setEnddate(LocalDate.of(2022, 3, 28));
			pHistoric.setModifieddate(LocalDate.of(2022, 4, 2));
			pHistoric.setProduct(productRepository.findById(1).get());
			pHistoric.setStandardcost(new BigDecimal(6000));
			costRepo.save(pHistoric);
			

		
			
			Productinventory inventary = new Productinventory();
			inventary.setLocation(locationN);
			inventary.setProduct(p);
			inventary.setQuantity(12);
			inventoryRepo.save(inventary);
			
			inventary = new Productinventory();
			inventary.setLocation(locationRepository.findById(1).get());
			inventary.setProduct(productRepository.findById(1).get());
			inventary.setQuantity(43);
			inventoryRepo.save(inventary);
			
		
		};
			
			
	}
}
