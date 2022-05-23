package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

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

import com.taller1SM.service.LocationService;
import com.taller1SM.service.LocationServiceImp;
import com.taller1SM.service.ProductService;
import com.taller1SM.service.ProductServiceImp;
import com.taller1SM.service.ProductcostHistoricImp;
import com.taller1SM.service.ProductinventoryImp;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = Taller1SmApplication.class)

@TestInstance(Lifecycle.PER_CLASS)
class AllTestTaller1 {

	// Product
	@InjectMocks
	private ProductServiceImp productService;
	// Location
	@InjectMocks
	private LocationServiceImp locationService;
	// ProductCostHistoric
	@InjectMocks
	private ProductcostHistoricImp productcostHistoricService;
	// ProductInventory
	@InjectMocks
	private ProductinventoryImp productinventoryService;

	// Mocks de repositorios
	@Mock
	private TProductDao productRepository;
	@Mock
	private ProductcategoryRepository productcategoryRepository;
	@Mock
	private ProductSubcategoryRepository productSubcategoryRepository;
	@Mock
	private TLocationDao locationRepository;
	@Mock
	private TProductCostHistoricDao productcosthistoryRepository;
	@Mock
	private TProductInventory productinventoryRepository;

	// inicialiaziador

	public AllTestTaller1() {
		productService = new ProductServiceImp(productRepository, productcategoryRepository,
				productSubcategoryRepository);
		locationService = new LocationServiceImp(locationRepository);
		productcostHistoricService = new ProductcostHistoricImp(productcosthistoryRepository, productRepository);
		productinventoryService = new ProductinventoryImp(productinventoryRepository, productRepository,
				locationRepository);
	}

	public Product setup1Pr() {
		Product pr = new Product();
		pr.setProductid(1);
		pr.setWeight((long) 3);
		pr.setSize((long) 10);
		pr.setProductnumber("0001");
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
	
		pr.setSellenddate(date2);
		pr.setSellstartdate(date);
		return pr;

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

		when(locationRepository.findById(1)).thenReturn(Optional.of(loc));

		when(locationRepository.findById(2)).thenReturn(Optional.of(loc));

		when(locationRepository.findById(3)).thenReturn(Optional.of(loc));

	}

	
	// ---------Pruebas de Product------------

	// SAVE PRODUCT

	// test que envia la excepcion si el producto es nulo
	@Test
	public void testSaveProductNull() {

		Product pr = null;
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, null, null);
		});
	}

	// Agregar un producto valido
	@Test
	public void testSaveProductFull() {
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();
		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) 10);
		// 26/03/2022
		// end > mayor

		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
	
		
	//when(productcategoryRepository.findById(1).get()).thenReturn(Optional.of(cr));
		//when(productSubcategoryRepository.findById(1).get()).thenReturn(Optional.of(prsub));
		pr.setSellenddate(date);
		pr.setSellstartdate(date2);
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	
	// Agregar un producto con tamaño -1

	@Test
	public void testSaveProductSizeInvalid() {
		
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
	
		
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();
		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) -1);
		pr.setSellenddate(date2);
		pr.setSellstartdate(date);

		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// Agregar un producto con peso < 0

	@Test
	public void testSaveProductWeigthInvalid() {
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
		
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();
		pr.setName("iphone");
		pr.setProductnumber("1111");
		pr.setWeight((long) -1);
		pr.setSize((long) 10);
		pr.setSellenddate(date2);
		pr.setSellstartdate(date);
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// validar fecha de inicio > a la de end

	@Test
	public void testSaveProductDate() {
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();

		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
		
		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) 10);
		pr.setProductnumber("00001");
		// 26/04/2022
		// star > mayor asi no funciona

		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		
		pr.setSellenddate(date);
		pr.setSellstartdate(date2);

		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// producto con nombre nulo
	@Test
	public void testSaveProductNameNull() {
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) 10);
		pr.setProductnumber("");
		// 26/04/2022
		// star > mayor asi no funciona

		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		pr.setSellenddate(date);
		pr.setSellstartdate(date2);
		
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// prueba para producto con subcategoria inexistente

	@Test
	public void testSaveProductInexistentSubCategory() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		prsub.setProductsubcategoryid(1);
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, null);
		});
	}

	// prueba para producto con categoria inexistente
	@Test
	public void testSaveProductInexistentCategory() {
		Product pr = setup1Pr();
		Productcategory prct = new Productcategory();
		prct.setProductcategoryid(1);
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prct));

		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, null, 1);
		});
	}

	// prueba para producto con id iguales
	@Test
	public void testSaveProductIdequals() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();

		//when(productRepository.findById(1).get()).thenReturn(Optional.of(pr));
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// prueba con category inexistente

@Test
	public void testSaveProductcategoryEmpty() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.empty());
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// prueba con subcategory Inexistent


	public void testSaveProductsubategoryEmpty() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();

		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// -------------EDIT PRODUCT------------------

	@Test
	public void productEditTestInvalidProductNumber() {

		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		pr.setProductnumber("");
		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(IllegalArgumentException.class, () -> {
			productService.editProduct(id, pr, id, id);
		});
	}

	@Test
	public void productEditTestInvalidDate() {

		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		pr.setSellenddate(date);
		pr.setSellstartdate(date2);
		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(IllegalArgumentException.class, () -> {
			productService.editProduct(id, pr, id, id);
		});
	}

	@Test
	public void productEditTestInvalidsize() {

		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		pr.setSize(-10);
		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(IllegalArgumentException.class, () -> {
			productService.editProduct(id, pr, id, id);
		});
	}

	@Test
	public void productEditTestInvalidWeigth() {

		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		pr.setWeight(-10);
		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(IllegalArgumentException.class, () -> {
			productService.editProduct(id, pr, id, id);
		});
	}

	@Test
	public void testEditProductcategoryInexist() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();

		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.empty());
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(RuntimeException.class, () -> {
			productService.editProduct(id, pr, id, id);
		});
	}

	@Test
	public void testEditProductsubcategoryInexist() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();

		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.empty());
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(RuntimeException.class, () -> {
			productService.editProduct(id, pr, id, id);
		});
	}

	@Test
	public void testEditProductNull() {
		Product pr = null;
		assertThrows(RuntimeException.class, () -> {
			productService.editProduct(null, pr, null, null);
		});

	}

	@Test
	public void testEditProductFull() {
		Product pr = setup1Pr();
		Productcategory cr = new Productcategory();

		Productsubcategory prsub = new Productsubcategory();

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		//when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		//when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(NoSuchElementException.class, () -> {
			productService.editProduct(null, pr, 1, 1);
		});

		
	

	}

	// ---------Pruebas de location------------

	// SAVE LOCATION

	@Test
	public void testSaveLocationNull() {
		Location l = null;

		assertThrows(RuntimeException.class, () -> {
			locationService.saveLocation(l);
		});

	}

	// localizacion guardada bien

	@Test
	public void testSaveLocationFull() {
		Location loc = new Location();
		loc.setLocationid(1);
		loc.setName("00005");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));

		assertDoesNotThrow(() -> {
			locationService.saveLocation(loc);
		});

	}

	// validar cantidad de caracetres en el nombre

	@Test
	public void testSaveLocationInvalidName() {

		Location loc2 = new Location();
		loc2.setLocationid(1);
		loc2.setName("1234");
		loc2.setAvailability(new BigDecimal(-1));
		loc2.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// disponibilidad menor a 1
	@Test
	public void testSaveLocationInvalidAvailability() {

		Location loc2 = new Location();
		loc2.setLocationid(2);
		loc2.setName("12345");
		loc2.setAvailability(new BigDecimal(-1));
		loc2.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// disponibilidad mayor a 10
	@Test
	public void testSaveLocationInvalidAvailabilityMajor1() {

		Location loc2 = new Location();
		loc2.setLocationid(2);
		loc2.setName("12345");
		loc2.setAvailability(new BigDecimal(11));
		loc2.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// disponibilidad null
	@Test
	public void testSaveLocationNullAvailability() {

		Location loc2 = new Location();
		loc2.setLocationid(2);
		loc2.setName("12345");
		loc2.setAvailability(null);
		loc2.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// tasa de costo mayor a 1
	@Test
	public void testSaveLocationInvalidCostRate() {

		Location loc2 = new Location();
		loc2.setLocationid(1);
		loc2.setName("12345");
		loc2.setAvailability(new BigDecimal(10));
		loc2.setCostrate(new BigDecimal(3));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// tasa de costo menor a 1
	@Test
	public void testSaveLocationInvalidCostRateMinor() {

		Location loc2 = new Location();
		loc2.setLocationid(1);
		loc2.setName("12345");
		loc2.setAvailability(new BigDecimal(10));
		loc2.setCostrate(new BigDecimal(-1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// tasa de costo null
	@Test
	public void testSaveLocationInvalidCostRateNull() {

		Location loc2 = new Location();
		loc2.setLocationid(1);
		loc2.setName("12345");
		loc2.setAvailability(new BigDecimal(10));
		loc2.setCostrate(null);

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

// Escenario 1 
	public Location setupLOC() {
		Location loc = new Location();
		loc.setLocationid(1);
		loc.setName("12345");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));
		return loc;
	}

	
	public void testSaveduplicateLocationid() {

		Location loc = setupLOC();

		//when(locationRepository.findById(1)).thenReturn(Optional.of(loc));

		assertThrows(NullPointerException.class, () -> {
			locationService.saveLocation(loc);
		});

	}

	// ------------------EDIT LOCATION----------------------------------------------

	// location id ocupado en objeto
	public Location LocationSetup2() {
		Location loc = new Location();
		loc.setLocationid(1);
		loc.setName("Cali007");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));
		return loc;
	}

	@Test
	public void testEditLocationNull() {
		Location l = null;

		assertThrows(RuntimeException.class, () -> {
			locationService.editLocation(1, l);
		});

	}

	@Test
	public void testEditLocationFull() {
		Location loc = new Location();
		loc.setLocationid(1);
		loc.setName("00005-bogota");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));

		assertThrows(NoSuchElementException.class, () -> {
			locationService.editLocation(1, loc);
		});

	}

	@Test
	public void testEditLocationInvalidName() {

		Location loc2 = LocationSetup2();
		loc2.setName("1234");

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.editLocation(1, loc2);
		});

	}

	// disponibilidad menor a 1
	@Test
	public void testEditLocationInvalidAvailability() {
		Location loc2 = LocationSetup2();
		loc2.setAvailability(new BigDecimal(-1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.editLocation(1, loc2);
		});

	}

	// disponibilidad myor a 10
	@Test
	public void testEditLocationInvalidAvailabilityMajor() {

		Location loc2 = LocationSetup2();
		loc2.setAvailability(new BigDecimal(11));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.editLocation(1, loc2);
		});

	}

	// disponibilidad null
	@Test
	public void testEditLocationNullAvailability() {

		Location loc2 = LocationSetup2();
		loc2.setAvailability(null);

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.editLocation(1, loc2);
		});

	}

	// tasa de costo mayor a 1
	@Test
	public void testEditLocationInvalidCostRate() {

		Location loc2 = LocationSetup2();
		loc2.setCostrate(new BigDecimal(3));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.editLocation(1, loc2);
		});

	}

	// tasa de costo menor a 1
	@Test
	public void testEditLocationInvalidCostRateMinor() {

		Location loc2 = LocationSetup2();
		loc2.setCostrate(new BigDecimal(-1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.editLocation(1, loc2);
		});

	}

	// tasa de costo menor a 1
	@Test
	public void testEditLocationInvalidCostRateNull() {

		Location loc2 = LocationSetup2();
		loc2.setCostrate(null);

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.editLocation(1, loc2);
		});

	}

	@Test
	public void testEditLocationemptyid() {

		Location loc = LocationSetup2();

		when(locationRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> {
			locationService.editLocation(1, loc);
		});

	}

	@Test
	public void testEditLocationnullid() {

		Location loc = LocationSetup2();
		loc.setLocationid(null);

		assertThrows(NoSuchElementException.class, () -> {
			locationService.editLocation(1, loc);
		});

	}

	// ---------PRUEBAS DE PRODUCT HISTORIC COST------------

	/// ----- SAVE COST HISTORIC -------

	// SETUP 1
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

		return prcH;
	}

	@Test
	public void testSaveProductCostHistoryNull() {

		Productcosthistory prcH = null;
		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, null);
		});

	}

	@Test
	public void testSaveProductCostHistoryfull() {

		Productcosthistory prcH = setupPHC();

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, 1);
		});

	}

	@Test
	public void testSaveProductCostHistoryCostEstandarMinor() {

		Productcosthistory prcH = setupPHC();

		prcH.setStandardcost(new BigDecimal(-10));

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, 1);
		});

	}

	@Test
	public void testSaveProductCostHistoryCostEstandarNull() {

		Productcosthistory prcH = setupPHC();

		prcH.setStandardcost(null);

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, 1);
		});

	}

	@Test
	public void testSaveProductCostHistoryDate() {

		Productcosthistory prcH = setupPHC();

		
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);
		prcH.setModifieddate(date);

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, 1);
		});

	}

	@Test
	public void testSaveProductCostHistoryDuplicatedId() {
		Product pr = new Product();
		Productcosthistory prcH = setupPHC();

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, 1);
		});

	}

	@Test
	public void testSaveProductCostHistoryProductIDempty() {

		Productcosthistory prcH = setupPHC();
		prcH.setId(1);

		//when(productRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, 1);
		});

	}

	// hay un producto con id null que se va a guardar en el cost historic produc
	@Test
	public void testSaveProductCostHistoryProductIDnull() {
		Product pr = new Product();
		Productcosthistory prcH = setupPHC();
		prcH.setId(null);

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, null);
		});

	}

	//--------- EDIT PRODUCT COST HISTORIC------

	@Test
	public void testEditProductCostHistoryNull() {

		Productcosthistory prcH = null;
		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.editPHC(null, prcH, null);
		});

	}

	@Test
	public void testEditProductCostHistoryfull() {

		Productcosthistory prcH = setupPHC();

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.editPHC(null, prcH, 1);
		});

	}

	@Test
	public void testEditProductCostHistoryCostEstandarMinor() {

		Productcosthistory prcH = setupPHC();

		prcH.setStandardcost(new BigDecimal(-10));

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.editPHC(null, prcH, 1);
		});

	}

	@Test
	public void testEditProductCostHistoryCostEstandarNull() {

		Productcosthistory prcH = setupPHC();

		prcH.setStandardcost(null);

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.editPHC(null, prcH, 1);
		});

	}

	@Test
	public void testEditProductCostHistoryDate() {

		Productcosthistory prcH = setupPHC();
		
		LocalDate date = LocalDate.of(2022, 3, 26);

		LocalDate date2 = LocalDate.of(2022, 4, 26);

		prcH.setModifieddate(date);

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.editPHC(null, prcH, 1);
		});

	}

	@Test
	public void testEditProductCostHistoryDuplicatedId() {
		Product pr = new Product();
		Productcosthistory prcH = setupPHC();

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.editPHC(null, prcH, 1);
		});

	}

	@Test
	public void testEditProductCostHistoryProductIDempty() {

		Productcosthistory prcH = setupPHC();
		prcH.setId(1);

		//when(productRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.editPHC(null, prcH, 1);
		});

	}

	// hay un producto con id null que se va a guardar en el cost historic produc
	@Test
	public void testEditProductCostHistoryProductIDnull() {
		Product pr = new Product();
		Productcosthistory prcH = setupPHC();
		prcH.setId(null);

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.editPHC(null, prcH, null);
		});

	}

	// ---------Pruebas de productInventory------------

	// SAVE PRODUCT INVENTORY

	public Productinventory setupPIR() {

		Productinventory pir = new Productinventory();
		pir.setId(1);
		pir.setQuantity(100);
		return pir;
	}

	// product null, location null
	@Test
	public void testSaveProductInventoryNull() {

		Productinventory prInvetory = null;

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.savePIR(prInvetory, null, null);
		});

	}

	public void testSaveProductInventoryfull() {
		Productinventory prInvetory = setupPIR();

		assertDoesNotThrow( () -> {
			productinventoryService.savePIR(prInvetory,1, 1);
		});
	}

/// location id nulo
	@Test
	public void productInventorySaveTestNullLocationId() {
		Productinventory prInventory = setupPIR();

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.savePIR(prInventory, null, 1);
		});
	}

	// product id nulo
	@Test
	public void productInventorySaveTestNullProductId() {
		Productinventory prInventory = setupPIR();

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.savePIR(prInventory, 1, null);
		});
	}

	@Test
	public void testSaveProductInventoryDuplicateId() {

		Productinventory prInvetory = setupPIR();
		// when(productinventoryRepository.findById(1)).thenReturn(Optional.of(prInvetory));

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.savePIR(prInvetory, 1, 1);
		});

	}

	// cantidad inferior a cero
	@Test
	public void testSaveProductInventoryQuantityMinor() {

		Productinventory prInvetory = setupPIR();

		prInvetory.setQuantity(-1);

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.savePIR(prInvetory, 1, 1);
		});

	}

	// cantidad null
	@Test
	public void testSaveProductInventoryQuantitynull() {

		Productinventory prInvetory = setupPIR();

		prInvetory.setQuantity(null);

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.savePIR(prInvetory, 1, 1);
		});

	}
	
	// invalid product
	@Test
	public void testSavePIRProductinexist() {
		Productinventory prInventory = setupPIR();
		Product pr= new Product();
		Location loc = new Location();

		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.empty());
		//when(productinventoryRepository.findById(1)).thenReturn(Optional.of(prInventory));
		//when(locationRepository.findById(1)).thenReturn(Optional.of(loc));

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(id, prInventory, id, id);
		});
	}
	
	// invalid location
	@Test
	public void testSavePIRLocationnexist() {
		Productinventory prInventory = setupPIR();
		Product pr= new Product();
		Location loc = new Location();

		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		//when(productinventoryRepository.findById(1)).thenReturn(Optional.of(prInventory));
		//when(locationRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(id, prInventory, id, id);
		});
	}
	
	// ------ EDIT PRODUCT INVENTORY  ------
	

	

	// product null, location null
	@Test
	public void testEditProductInventoryNull() {

		Productinventory prInvetory = null;

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(null, prInvetory, null, null);
		});

	}

	// modificado con exito 
	public void testEditProductInventoryFull() {
		Productinventory prInvetory = setupPIR();
		setupPIR().setQuantity(90);

		assertDoesNotThrow( () -> {
			productinventoryService.editPIR(null, prInvetory, 1, 1);
		});
	}

/// location id nulo
	@Test
	public void testEditproductInventoryNullLocationId() {
		Productinventory prInventory = setupPIR();

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(null, prInventory, null, 1);
		});
	}

	// product id nulo
	@Test
	public void testEditproductInventoryNullProductId() {
		Productinventory prInventory = setupPIR();

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(null, prInventory, 1, null);
		});
	}

	@Test
	public void testEditProductInventoryDuplicateId() {

		Productinventory prInvetory = setupPIR();
		// when(productinventoryRepository.findById(1)).thenReturn(Optional.of(prInvetory));

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(null, prInvetory, 1, 1);
		});

	}
	
	// invalid product
	@Test
	public void testEditPIRProductinexist() {
		Productinventory prInventory = setupPIR();
		Product pr= new Product();
		Location loc = new Location();

		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.empty());
		//when(productinventoryRepository.findById(1)).thenReturn(Optional.of(prInventory));
		//when(locationRepository.findById(1)).thenReturn(Optional.of(loc));

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(id, prInventory, id, id);
		});
	}
	
	// invalid location
	@Test
	public void testEditPIRLocationnexist() {
		Productinventory prInventory = setupPIR();
		Product pr= new Product();
		Location loc = new Location();

		Integer id = 1;

		//when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		///when(productinventoryRepository.findById(1)).thenReturn(Optional.of(prInventory));
		//when(locationRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(id, prInventory, id, id);
		});
	}

	// cantidad inferior a cero
	@Test
	public void testEditProductInventoryQuantityMinor() {

		Productinventory prInvetory = setupPIR();

		prInvetory.setQuantity(-1);

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(null, prInvetory, 1, 1);
		});

	}

	// cantidad null
	@Test
	public void testEditProductInventoryQuantitynull() {

		Productinventory prInvetory = setupPIR();

		prInvetory.setQuantity(null);

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.editPIR(null, prInvetory, 1, 1);
		});

	}


}
