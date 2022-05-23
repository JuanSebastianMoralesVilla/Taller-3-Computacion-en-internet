package com.taller1SM.repositories;

import org.springframework.data.repository.CrudRepository;

import com.taller1SM.model.prod.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{

}
