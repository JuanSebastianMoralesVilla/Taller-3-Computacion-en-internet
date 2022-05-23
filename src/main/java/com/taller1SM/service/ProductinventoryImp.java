package com.taller1SM.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.taller1SM.dao.TLocationDao;
import com.taller1SM.dao.TProductDao;
import com.taller1SM.dao.TProductInventory;
import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productinventory;

@Service
public class ProductinventoryImp implements ProductinventoryService {

	private TProductInventory pirDAO;
	private TProductDao productDao;
	private TLocationDao locationDao;

	public ProductinventoryImp(TProductInventory pirDAO,
			TProductDao productDao, TLocationDao locationDao) {

		this.pirDAO = pirDAO;
		this.productDao = productDao;
		this.locationDao = locationDao;
	}

	@Override
	@Transactional
	public Productinventory savePIR(Productinventory productinventory, Integer productId, Integer locationId) {


		if (productinventory.getQuantity() == null) {
			throw new NullPointerException("CANTIDAD ES NULA.");
		}
		if (productinventory.getQuantity() < 0) {
			throw new IllegalArgumentException("CANTIDAD ES MENOR A CERO.");
		}

		productinventory.setProduct(productDao.findById(productId).get());
		productinventory.setLocation(locationDao.findById(locationId).get());

		 pirDAO.save(productinventory);
		 return productinventory;
	}
	
	@Override
	@Transactional
	public Productinventory editPIR(Integer id, Productinventory productinventory, Integer productId, Integer locationId) {

	
		if (productinventory.getQuantity()== null) {
			throw new RuntimeException("no valid");

		} 
		
		/*
		if (productinventory.getQuantity() < 0) {
			throw new IllegalArgumentException("Cantidad mayor a 0");

		}
*/
		
		productinventory.setLocation(locationDao.findById(locationId).get());
		productinventory.setProduct(productDao.findById(productId).get());
		
		
		Productinventory inventoryModify = pirDAO.findById(id).get();
		inventoryModify.setLocation(locationDao.findById(locationId).get());
		inventoryModify.setProduct(productDao.findById(productId).get());
		inventoryModify.setQuantity(productinventory.getQuantity());
		 pirDAO.save(inventoryModify);
		 return inventoryModify;
	}// fin metodo

	@Override
	public Iterable<Productinventory> findAll() {
		return pirDAO.findAll();
	}

	@Override
	public Optional<Productinventory> findById(Integer id) {

		return pirDAO.findById(id);
	}

}
