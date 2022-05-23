package com.taller1SM.service;

import java.util.Optional;

import com.taller1SM.model.prod.Productcosthistory;

public interface ProductcosthistoryService {

	public Productcosthistory savePHC(Productcosthistory productcosthistory, Integer productId);

	public Productcosthistory editPHC(Integer id, Productcosthistory productcosthistory, Integer productId);
	
	public Iterable<Productcosthistory> findAll();

	public Optional<Productcosthistory> findById(Integer id);
}
