package com.taller1SM.service;

import java.math.BigDecimal;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.taller1SM.dao.TLocationDao;
import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.model.prod.Productsubcategory;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;

@Service
public class LocationServiceImp implements LocationService {

	// nombre al menos 5 caracteres, disponibilidad 1 y 10, tasa de costo 0 y 1

	private TLocationDao locationDao;

	public LocationServiceImp(TLocationDao locationDao) {
		this.locationDao = locationDao;

	}

	@Override
	@Transactional
	public void saveLocation(Location location) {
		if (location == null ) {
			throw new RuntimeException();
		

		} else if (location.getName().length() < 5) {
			throw new IllegalArgumentException("El nombre debe tener 5 o mas caracteres");
		} else if (location.getAvailability()==null||location.getAvailability().compareTo(new BigDecimal("1")) == -1
				|| (location.getAvailability().compareTo(new BigDecimal("10")) == 1)) {
			throw new IllegalArgumentException("La disponibilidad debe ser un valor entre 1 y 10 ");
		} else if (location.getCostrate()==null||location.getCostrate().compareTo(new BigDecimal("0")) == -1
				|| (location.getCostrate().compareTo(new BigDecimal("1")) == 1) ) {
			throw new IllegalArgumentException("La debe ser un valor entre 1 y 0");
		} else {
			locationDao.save(location);

		}

	}

	@Override
	@Transactional
	public Location editLocation(Integer  id,Location location) {

		if (location == null || id== null) {
			throw new NullPointerException("location no exist");
		} 
		
		if  (location.getCostrate()==null||location.getCostrate().compareTo(new BigDecimal("0")) == -1
				|| (location.getCostrate().compareTo(new BigDecimal("1")) == 1) )  {
			throw new IllegalArgumentException("La debe ser un valor entre 1 y 0");
		}
		
		if (location.getAvailability()==null||location.getAvailability().compareTo(new BigDecimal("1")) == -1
				|| (location.getAvailability().compareTo(new BigDecimal("10")) == 1))  {
			throw new IllegalArgumentException("La disponibilidad debe ser un valor entre 1 y 10 ");
		}
		

		if (location.getName().length() < 5) {
			throw new IllegalArgumentException("El nombre debe tener 5 o mas caracteres");
		}

		Location locationModified = locationDao.findById(id).get();
		
		locationModified.setAvailability(location.getAvailability());
		locationModified.setCostrate(location.getCostrate());
		locationModified.setName(location.getName());
	  locationDao.save(locationModified);
        return locationModified;
		

	}
	
	public Iterable<Location> findAll() {
		return locationDao.findAll();
	}
	
	@Override
	public Optional<Location> findById(Integer id) {
		
		return locationDao.findById(id);
	}

}// fin clase
