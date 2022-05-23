package com.example.demo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.constraints.AssertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taller1SM.Taller1SmApplication;
import com.taller1SM.dao.TLocationDao;
import com.taller1SM.dao.TProductCostHistoricDao;
import com.taller1SM.dao.TProductDao;
import com.taller1SM.dao.TProductInventory;
import com.taller1SM.dao.TProductModelDao;
import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.model.prod.Productmodel;
import com.taller1SM.model.prod.Productsubcategory;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;

@SpringBootTest
@ContextConfiguration(classes = Taller1SmApplication.class)
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
public class TestDao {
	@Autowired
	EntityManager entityManager;
	@Autowired
	TProductDao productDao;
	@Autowired
	TLocationDao locationDao;
	@Autowired
	TProductCostHistoricDao phcDao;
	@Autowired
	TProductInventory pirDao;
	@Autowired
	TProductModelDao modeldao;

	@Autowired
	ProductSubcategoryRepository prsub;

// Aqui se prueban los metodos de save, update, delete de los daos de cada entidad asignadada como
// Product, Product cost history, Product inventory, Product location
//_________PRODUCT TEST____________

	public Product setup1Pr() {
		Product pr = new Product();
		pr.setProductid(1);
		pr.setName("iphone");
		pr.setWeight((long) 3);
		pr.setSize((long) 10);
		pr.setProductnumber("0001");
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);

		pr.setSellenddate(date2);
		pr.setSellstartdate(date);
		return pr;

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void SaveProductTest() {

		Product pr = new Product();
		pr.setName("iphone");

		pr.setWeight((long) 3);
		pr.setSize((long) 10);
		pr.setProductnumber("0001");
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);

		pr.setSellenddate(date2);
		pr.setSellstartdate(date);

		productDao.save(pr);
		assertNotNull(productDao);

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UpdateProductTest() {

		Product pr = setup1Pr();

		pr.setProductnumber("00002");
		productDao.update(pr);

		assertTrue(entityManager.find(Product.class, pr.getProductid()).getProductnumber().equals("00002"));

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void DeleteProductTest() {

		Product pr = new Product();
		pr.setName("iphone");

		pr.setWeight((long) 3);
		pr.setSize((long) 10);
		pr.setProductnumber("0001");
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);

		pr.setSellenddate(date2);
		pr.setSellstartdate(date);

		productDao.save(pr);
		productDao.delete(pr);
		assertNotNull(productDao);

	}

	// _________LOCATION TEST____________

	public Location LocationSetup2() {
		Location loc = new Location();
		loc.setLocationid(1);
		loc.setName("Cali007");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));
		return loc;
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void SaveLocationTest() {

		Location loc = new Location();

		loc.setName("00005");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));

		// productDao.save(pr);
		// assertNotNull(entityManager.find(Product.class, pr.getProductid()));

		locationDao.save(loc);
		assertNotNull(locationDao);

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void DeleteLocationTest() {

		assertNotNull(locationDao);

		Location loc = new Location();

		loc.setName("00005");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));
		locationDao.save(loc);
		locationDao.delete(loc);

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UpdateLocationTest() {

		Location loc2 = LocationSetup2();
		loc2.setName("MIAMI-FLORIDA");
		assertNotNull(locationDao);

		locationDao.update(loc2);

		assertTrue(entityManager.find(Location.class, loc2.getLocationid()).getName().equals("MIAMI-FLORIDA"));
	}

	// _________PRODUCT COST HISTORIC TEST____________

	public Productcosthistory setupPHC() {
		Productcosthistory prcH = new Productcosthistory();
		Product pr = new Product();
		pr.setProductid(1);
		prcH.setProduct(pr);
		prcH.setId(1);
		prcH.setStandardcost(new BigDecimal(10));

		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
		prcH.setEnddate(date2);
		prcH.setModifieddate(date);
		return prcH;
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void SavePHCTest() {

		Productcosthistory prcH = new Productcosthistory();
		// Product pr = new Product();

		// prcH.setProduct(pr);

		prcH.setStandardcost(new BigDecimal(10));
		LocalDate date2 = LocalDate.of(2022, 4, 26);
		prcH.setEnddate(date2);
		prcH.setModifieddate(LocalDate.of(2022, 4, 28));
		phcDao.save(prcH);
		assertNotNull(phcDao);

		// productDao.save(pr);
		// assertNotNull(entityManager.find(Product.class, pr.getProductid()));

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UpdatePHCTest() {
		;

		Productcosthistory prcH = setupPHC();
		LocalDate date = LocalDate.of(2022, 4, 28);
		prcH.setEnddate(date);
		phcDao.update(prcH);

		// assertNotNull(entityManager.find(Productcosthistory.class, prcH.getId()));
		assertTrue(phcDao.findById(prcH.getId()).get().getEnddate().compareTo(date) == 0);

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void DeletePHCTest() {

		Productcosthistory prcH = new Productcosthistory();
		// Product pr = new Product();

		// prcH.setProduct(pr);

		prcH.setStandardcost(new BigDecimal(10));
		LocalDate date2 = LocalDate.of(2022, 4, 26);
		prcH.setEnddate(date2);
		prcH.setModifieddate(LocalDate.of(2022, 4, 28));
		phcDao.save(prcH);
		phcDao.delete(prcH);
		assertNotNull(phcDao);
	}

	// _________PRODUCT INVENTORY TEST____________

	public Productinventory setupPIR() {

		Productinventory pir = new Productinventory();
		pir.setId(1);
		pir.setQuantity(200);
		return pir;
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void SavePirTest() {

		Productinventory pir = new Productinventory();

		pir.setQuantity(100);

		pirDao.save(pir);

		assertNotNull(pir);

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void DeletePirTest() {

		Productinventory pir = new Productinventory();

		pir.setQuantity(100);

		pirDao.save(pir);
		assertNotNull(pirDao);
		pirDao.delete(pir);
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UpdatePirTest() {

		Productinventory pir = setupPIR();

		pir.setQuantity(200);
		Integer n = 200;

		pirDao.update(pir);
		assertTrue(pirDao.findById(pir.getId()).get().getQuantity().compareTo(n) == 0);

	}
	
	
	//_______________Product model_____________
	
	public Productmodel productModelSetup() {
	Productmodel pm= new Productmodel() ;
	pm.setProductmodelid(1);
	pm.setName("Modelo de producto");
	pm.setInstructions("dsdsdsdsdsdsdssdsdsds");
	pm.setModifieddate(new Timestamp(System.currentTimeMillis()));
    pm.setCatalogdescription("asasasasasasasasas");
    pm.setRowguid(2);
	
return pm;
	}

	
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void SaveProductmodelTest() {

		
		Productmodel pm= new Productmodel() ;
		pm.setProductmodelid(1);
		pm.setName("Modelo de producto");
		pm.setInstructions("dsdsdsdsdsdsdssdsdsds");
		pm.setModifieddate(new Timestamp(System.currentTimeMillis()));
	    pm.setCatalogdescription("asasasasasasasasas");
	    pm.setRowguid(2);

		modeldao.save(pm);
		
		
			assertNotNull(modeldao);


	}

	//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void DeleteProductModelTest() {

	

		Productmodel pm= new Productmodel() ;
		pm.setProductmodelid(1);
		pm.setName("Modelo de producto");
	

		modeldao.save(pm);
		modeldao.delete(pm);
		assertNotNull(pm);

	}

	
	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void UpdateProductModelTest() {

		
		Productmodel pm=productModelSetup();
		
		pm.setName("Hola");
		

		modeldao.update(pm);
		
		assertNotNull(modeldao);
		//assertTrue(modeldao.findById(pm.getProductmodelid().getName().get.equals("Hola")));


		
	}
	


}
