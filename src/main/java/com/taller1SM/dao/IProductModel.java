package com.taller1SM.dao;

import java.util.Optional;

import com.taller1SM.model.prod.Productmodel;

public interface IProductModel {

	void save(Productmodel entity);

	void update(Productmodel entity);

	void delete(Productmodel entity);

	Optional<Productmodel> findById(Integer productid);

}
