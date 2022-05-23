package com.taller1SM.dao;

import java.util.List;
import java.util.Optional;

import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productinventory;

public interface IProductInventory {

	void save(Productinventory entity);

	void update(Productinventory entity);

	void delete(Productinventory entity);

	Optional<Productinventory> findById(Integer id);

	List<Productinventory> findByProductid(Integer idproduct);

	List<Productinventory> findByLocationID(Integer idlocation);

}
