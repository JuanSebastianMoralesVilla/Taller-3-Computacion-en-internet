package com.taller1SM.repositories;

import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.repository.CrudRepository;

import com.taller1SM.model.prod.Productmodel;

public interface ProductModelRepository  extends CrudRepository<Productmodel, Integer>{

}
