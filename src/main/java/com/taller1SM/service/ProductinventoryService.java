package com.taller1SM.service;

import java.util.Optional;

import com.taller1SM.model.prod.Productinventory;

public interface ProductinventoryService {
	
	public Iterable<Productinventory> findAll();
	 public Optional<Productinventory> findById(Integer id);
		public Productinventory editPIR(Integer id,Productinventory productinventory, Integer productId, Integer locationId);
		public Productinventory savePIR(Productinventory productinventory, Integer productId, Integer locationId ) ;
}
