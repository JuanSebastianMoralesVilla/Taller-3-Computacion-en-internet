package com.taller1SM.repositories;

import org.springframework.data.repository.CrudRepository;

import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.model.prod.Productsubcategory;

public interface ProductSubcategoryRepository extends CrudRepository<Productsubcategory, Integer>{

}
