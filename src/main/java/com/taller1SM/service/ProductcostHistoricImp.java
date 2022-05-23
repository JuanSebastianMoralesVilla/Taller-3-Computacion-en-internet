package com.taller1SM.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.taller1SM.dao.TProductCostHistoricDao;
import com.taller1SM.dao.TProductDao;
import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcosthistory;

@Service
public class ProductcostHistoricImp implements ProductcosthistoryService {

	private TProductDao productDao;
	private TProductCostHistoricDao productcosthistoryDao;

	public ProductcostHistoricImp(TProductCostHistoricDao productcosthistoryDao,
			TProductDao productDao) {
		this.productDao = productDao;
		this.productcosthistoryDao = productcosthistoryDao;

	}

	@Override
	@Transactional
	public Productcosthistory savePHC(Productcosthistory productcosthistory, Integer productId) {

		if (productcosthistory.getModifieddate().isAfter(productcosthistory.getEnddate())) {
			throw new IllegalArgumentException("La fecha de inicio de venta debe ser menor a la fecha de fin");

		}

		if (productcosthistory.getStandardcost() == null) {
			throw new IllegalArgumentException("El costo estandar no puede ser nulo");
		}

		if (productcosthistory.getStandardcost().intValue() < 0) {
			throw new IllegalArgumentException("El costo estandar debe ser mayor a 0");
		}

		Product product = productDao.findById(productId).get();

		productcosthistory.setProduct(product);
		 productcosthistoryDao.save(productcosthistory);
		 return productcosthistory;
	}

	@Override
	@Transactional
	public Productcosthistory editPHC(Integer id, Productcosthistory productcosthistory, Integer productId) {

		if (productcosthistory == null || productId == null || id == null) {
			throw new NullPointerException("id are null.");
		}

		if (productcosthistory.getModifieddate().isAfter(productcosthistory.getEnddate())) {
			throw new IllegalArgumentException("La fecha de inicio de venta debe ser menor a la fecha de fin");
		}

		if (productcosthistory.getStandardcost().intValue() < 0) {
			throw new IllegalArgumentException("costo estandar no debe ser negativo");
		}

		//if (!productcosthistoryDao.existsById(productcosthistory.getId())) {
		//	throw new IllegalStateException(" no existen datos para modificar");
		//}

		//if (!productDao.existsById(productId)) {
		//	throw new NullPointerException("Product no existe.");
		//}
	
		
		Productcosthistory productCHmodified = productcosthistoryDao.findById(id).get();
		productCHmodified.setModifieddate(productcosthistory.getModifieddate());
		productCHmodified.setEnddate(productcosthistory.getEnddate());
		productCHmodified.setStandardcost(productcosthistory.getStandardcost());
		productCHmodified.setProduct(productcosthistory.getProduct());
		productcosthistoryDao.save(productCHmodified);
		 productcosthistoryDao.findById(productCHmodified.getId()).get();
		 return productCHmodified;
	}

	public Iterable<Productcosthistory> findAll() {
		return productcosthistoryDao.findAll();
	}

	public Optional<Productcosthistory> findById(Integer id) {

		return productcosthistoryDao.findById(id);
	}
	
	

}
