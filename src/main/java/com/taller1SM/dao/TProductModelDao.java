package com.taller1SM.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.taller1SM.model.prod.Productmodel;

@Repository
@Scope("singleton")
public class TProductModelDao implements IProductModel {
	
	@PersistenceContext
	private EntityManager entityManagerProduct;

	@Override
	public void save(Productmodel entity) {
		entityManagerProduct.persist(entity);
		System.out.println("guardado en el dao bd");
	}

	@Override
	public void update(Productmodel entity) {
		entityManagerProduct.merge(entity);
		System.out.println("modificado con dao bd");
	}

	@Override
	public void delete(Productmodel entity) {
		entityManagerProduct.remove(entity);
		System.out.println("Eliminado con dao bd");
	}

	@Override
	public Optional<Productmodel> findById(Integer productid) {
		return Optional.ofNullable(entityManagerProduct.find(Productmodel.class, productid));
	}


}
