package com.example.demo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.model.prod.Productsubcategory;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;

@SpringBootTest
@ContextConfiguration(classes = Taller1SmApplication.class)
@Rollback(false)
@TestInstance(Lifecycle.PER_CLASS)
public class AllTestDao {
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
	ProductSubcategoryRepository prsub;

	// Aqui se prueban consultas y algunas cosas de los metodos de save, update,
	// delete por cada entidad

	// _________QUERY TEST______________

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByIdProductSubCategory() {
		setupProducts();
		List<Product> list = (List<Product>) productDao.findBySubcategoryid(1);
		assertEquals(1, list.size());
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByIdProductModelTest() {
		setupProducts();

		List<Product> list = (List<Product>) productDao.findByproductModel(0);
		assertEquals(0, list.size());
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findBysizeunitmeasureTestFull() {
		setupProducts();
		List<Product> list = (List<Product>) productDao.findBySizeunitmeasure("0");
		assertEquals(0, list.size());
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByStandardCostTest1() {
		seupPhc();
		List<Productcosthistory> list = (List<Productcosthistory>) phcDao.findByStandardCost(new BigDecimal(1));
		assertEquals(0, list.size());
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByProductTestPHC() {
		seupPhc();
		List<Productcosthistory> list = (List<Productcosthistory>) phcDao.findByProduct(1);
		assertEquals(1, list.size());
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByLocationIDTest1() {
		seupPir();
		List<Productinventory> list = (List<Productinventory>) pirDao.findByLocationID(1);

		// List<Productinventory> list3 = (List<Productinventory>)
		// pirDao.findByProductid(3);
		assertEquals(1, list.size());
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByProductidTestPIR() {
		seupPir();
		List<Productinventory> list = (List<Productinventory>) pirDao.findByProductid(1);

		// List<Productinventory> list3 = (List<Productinventory>)
		// pirDao.findByProductid(3);
		assertEquals(1, list.size());

	}

	


	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void especialQuery1Test() {
		
		List<Object[]> result = productDao.findByProductSumInventory_orderByLocation(prsub.findById(1).get());
		Product p1 = (Product) result.get(0)[0];
		Long count = (Long) result.get(0)[1];
		assertAll(() -> assertEquals(1,result.size()),
				() -> assertEquals(1,p1.getProductid()),
		
				() -> assertEquals(1,count));
	}

	

	public void setupPHC2(){
		
		
		
	
		
	
	
		
	}
	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void especialQuery2Test() {
		

		
		List<Product> result = productDao.findByProductCostHistoric();
		assertAll(() -> assertEquals(0,result.size())
				);
		
	}
	

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void especialQuery2FullTest() {
		
		
		

		Product pr1= productDao.findById(1).get();
		
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
		
		Productcosthistory phc1= new Productcosthistory();
		phc1.setProduct(pr1);
		phc1.setProduct(pr1);
		//phc1.setId(1);
		phc1.setStandardcost(new BigDecimal(10));

	
		phc1.setEnddate(date2);
		phc1.setModifieddate(date);
		phcDao.save(phc1);
		
		
		
		Productcosthistory phc2=new Productcosthistory();
		phc2.setProduct(pr1);
		phc2.setProduct(pr1);
		
		phc2.setStandardcost(new BigDecimal(10));
		phc2.setEnddate(date2);
		phc2.setModifieddate(date);
		
		phcDao.save(phc2);
		
		List<Product> result = productDao.findByProductCostHistoric();
	//2
		
		assertAll(() -> assertEquals(1,result.size())
				);
		
	}
//_________SETUP TO QUERYS____________

	@BeforeAll
	public void seupPir() {
		Productinventory prInventory = setupPIR();
		Product pr = new Product();
		Location loc = new Location();
		prInventory.setId(1);
		pr.setProductid(1);
		loc.setLocationid(1);

	}

	@BeforeAll
	public void seupPhc() {
		Productcosthistory prcH = new Productcosthistory();
		Product pr = new Product();
		pr.setProductid(1);
		prcH.setProduct(pr);
		prcH.setId(1);
		prcH.setStandardcost(new BigDecimal(1));

		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
		prcH.setEnddate(date2);

	}

	@BeforeAll
	public void setup1Location() {
		Location loc = new Location();
		// 1 elemento
		loc.setLocationid(1);
		loc.setName("00005");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));
		// 2 elemento
		loc.setLocationid(2);
		loc.setName("123456");
		loc.setAvailability(new BigDecimal(9));
		loc.setCostrate(new BigDecimal(0));
		// 3 elemento
		loc.setLocationid(3);
		loc.setName("12345");
		loc.setAvailability(new BigDecimal(3));
		loc.setCostrate(new BigDecimal(1));

	}

	@BeforeAll
	public void setupProducts() {

		Product pr = new Product();
		pr.setProductid(1);
		pr.setName("iphone");
		pr.setWeight((long) 3);
		pr.setSize((long) 10);
		pr.setProductnumber("0001");
		pr.setProductmodel(null);
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);

		pr.setSellenddate(date2);
		pr.setSellstartdate(date);

		Product pr2 = new Product();
		pr2.setProductid(2);
		pr2.setName("iphone");
		pr2.setWeight((long) 3);
		pr2.setSize((long) 10);
		pr2.setProductnumber("0001");
		pr2.setSellenddate(date2);
		pr2.setSellstartdate(date);

		Product pr3 = new Product();
		pr3.setProductid(2);
		pr3.setName("iphone");
		pr3.setWeight((long) 3);
		pr3.setSize((long) 10);
		pr3.setProductnumber("0001");
		pr.setSellenddate(date2);
		pr.setSellstartdate(date);
	}

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
	public void findByIdProduct() {
		assertAll("GroupedAssertionHeading", () -> assertEquals(1, productDao.findById(1).get().getProductid()),
				() -> assertEquals(2, productDao.findById(2).get().getProductid()),
				() -> assertEquals(3, productDao.findById(3).get().getProductid())

		);

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
	public void findByIdLocation() {
		assertAll("GroupedAssertionHeading", () -> assertEquals(1, locationDao.findById(1).get().getLocationid())

		);

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
	public void findByIdPchTest() {
		assertAll("GroupedAssertionHeading", () -> assertEquals(1, phcDao.findById(1).get().getId())

		);

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

		assertNotNull(phcDao);

		Productcosthistory prcH = new Productcosthistory();
		// Product pr = new Product();

		// prcH.setProduct(pr);

		prcH.setStandardcost(new BigDecimal(10));
		LocalDate date2 = LocalDate.of(2022, 4, 26);
		prcH.setEnddate(date2);
		prcH.setModifieddate(LocalDate.of(2022, 4, 28));
		phcDao.save(prcH);
		phcDao.delete(prcH);

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

		assertNotNull(pirDao);

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

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByIdPIR() {
		assertAll("GroupedAssertionHeading", () -> assertEquals(1, pirDao.findById(1).get().getId())

		);

	}

	// _________ OTHER TEST____________________
	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void SaveProductTest2() {

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
	public void UpdateProductTest2() {

		Product pr = setup1Pr();

		pr.setProductnumber("00003");
		productDao.update(pr);

		assertTrue(entityManager.find(Product.class, pr.getProductid()).getProductnumber().equals("00003"));

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void DeleteProductTest2() {

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

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void SaveLocationTest2() {

		Location loc = new Location();

		loc.setName("00005");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));

		// productDao.save(pr);
		// assertNotNull(entityManager.find(Product.class, pr.getProductid()));

		locationDao.save(loc);
		assertNotNull(entityManager.find(Location.class, loc.getLocationid()));

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void DeleteLocationTest2() {

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
	public void UpdateLocationTest2() {

		Location loc2 = LocationSetup2();
		loc2.setName("MIAMI-FLORIDA");
		assertNotNull(locationDao);

		locationDao.update(loc2);

		assertTrue(entityManager.find(Location.class, loc2.getLocationid()).getName().equals("MIAMI-FLORIDA"));
	}

	// _________PRODUCT COST HISTORIC TEST____________

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void SavePHCTest2() {

		Productcosthistory prcH = new Productcosthistory();
		// Product pr = new Product();

		// prcH.setProduct(pr);

		prcH.setStandardcost(new BigDecimal(10));
		LocalDate date2 = LocalDate.of(2022, 4, 26);
		prcH.setEnddate(date2);
		prcH.setModifieddate(LocalDate.of(2022, 4, 28));
		phcDao.save(prcH);
		assertNotNull(entityManager.find(Productcosthistory.class, prcH.getId()));

		// productDao.save(pr);
		// assertNotNull(entityManager.find(Product.class, pr.getProductid()));

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UpdatePHCTest2() {
		;

		Productcosthistory prcH = setupPHC();
		LocalDate date = LocalDate.of(2022, 4, 28);
		prcH.setEnddate(date);

		assertNotNull(entityManager.find(Productcosthistory.class, prcH.getId()));
		// assertTrue(entityManager.find(Productcosthistory.class,
		// prcH.getId()).getEnddate().compareTo(date));

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void DeletePHCTest2() {

		assertNotNull(phcDao);

		Productcosthistory prcH = new Productcosthistory();
		// Product pr = new Product();

		// prcH.setProduct(pr);

		prcH.setStandardcost(new BigDecimal(10));
		LocalDate date2 = LocalDate.of(2022, 4, 26);
		prcH.setEnddate(date2);
		prcH.setModifieddate(LocalDate.of(2022, 4, 28));
		phcDao.save(prcH);
		phcDao.delete(prcH);

	}

	// _________PRODUCT INVENTORY
	// TEST_________________________________________________________________________________

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void SavePirTest2() {

		Productinventory pir = new Productinventory();

		pir.setQuantity(100);

		pirDao.save(pir);

		assertNotNull(pirDao);
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void DeletePirTest2() {

		Productinventory pir = new Productinventory();

		pir.setQuantity(100);

		pirDao.save(pir);
		assertNotNull(pirDao);
		pirDao.delete(pir);
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void UpdatePirTest2() {

		Productinventory pir = setupPIR();

		pir.setQuantity(200);
		Integer n = 200;
		assertNotNull(entityManager.find(Productinventory.class, pir.getId()));
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByproductModelTest() {
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findBySizeunitmeasureTest() {
		List<Product> list = (List<Product>) productDao.findByproductModel(0);
		assertEquals(0, list.size());
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findBySubcategoryidTest() {
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByStandardCostTest() {
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByProduct() {
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByLocationIDTest() {
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void findByProductidTest() {
	}
}
