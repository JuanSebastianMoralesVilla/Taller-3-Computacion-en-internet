package com.taller1SM.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.taller1SM.model.prod.Productinventory;

@Repository
//@Scope("singleton")
public class TProductInventory implements IProductInventory {

	@PersistenceContext
	private EntityManager entityManagerProductPI;

	@Override
	public void save(Productinventory entity) {
		entityManagerProductPI.persist(entity);
System.out.println("guardo un pir");
	}

	@Override
	public void update(Productinventory entity) {
		entityManagerProductPI.merge(entity);

	}

	@Override
	public void delete(Productinventory entity) {
		entityManagerProductPI.remove(entity);

	}

	@Override
	public Optional<Productinventory> findById(Integer id) {
		return Optional.ofNullable(entityManagerProductPI.find(Productinventory.class, id));
	}

	@Override
	public List<Productinventory> findByProductid(Integer idproduct) {
		String jpql = "SELECT i FROM Productinventory i WHERE i.product.productid= :idproduct";
		TypedQuery<Productinventory> query = entityManagerProductPI.createQuery(jpql, Productinventory.class);
		return query.setParameter("idproduct", idproduct).getResultList();
	}

	@Override
	public List<Productinventory> findByLocationID(Integer idlocation) {
		String jpql = "SELECT i FROM Productinventory i WHERE i.location.locationid= :idlocation";
		TypedQuery<Productinventory> query = entityManagerProductPI.createQuery(jpql, Productinventory.class);
		return query.setParameter("idlocation", idlocation).getResultList();
	}

	public List<Productinventory> findAll() {
		Query query = entityManagerProductPI.createQuery("SELECT pir FROM Productinventory pir");
		return query.getResultList();
	}

//Permita que los inventarios de productos se puedan buscar por id de producto y por id de la ubicación independientemente.
}
