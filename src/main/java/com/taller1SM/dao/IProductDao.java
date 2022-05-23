package com.taller1SM.dao;

import java.util.List;
import java.util.Optional;

import com.taller1SM.model.prod.Product;

public interface IProductDao {

	void save(Product entity);

	void update(Product entity);

	void delete(Product entity);

	Optional<Product> findById(Integer productid);

	List<Product> findAll();



	List<Product> findByproductModel(Integer modelid);

	List<Product> findBySizeunitmeasure(String id);

	List<Product> findBySubcategoryid(Integer subcategoryID);


}
