package com.taller1SM.service;

import java.util.Optional;

import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productsubcategory;

public interface ProductService {

	public Product saveProduct(Product product, Integer prCategoryId, Integer prScategoryId);

	
	public Iterable<Product> findAll();
	public Iterable<Productsubcategory> findAllSubcategory();

	public Optional<Product> findById(Integer id);

	public Product editProduct(Integer id, Product product, Integer prCategoryId, Integer prSubcategoryId);


	void delete(Product product);
}
