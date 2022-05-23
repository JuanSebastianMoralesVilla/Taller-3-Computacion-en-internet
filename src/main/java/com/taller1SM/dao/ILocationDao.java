package com.taller1SM.dao;

import java.util.List;
import java.util.Optional;

import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;

public interface ILocationDao {

	void save(Location entity);

	void update(Location entity);

	void delete(Location entity);



	List<Location> findAll();

	Optional<Location> findById(Integer id);

}
