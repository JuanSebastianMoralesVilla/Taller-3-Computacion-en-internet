package com.taller1SM.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.taller1SM.model.prod.Location;


@Repository
@Scope("singleton")
public class TLocationDao implements ILocationDao {

	
	@PersistenceContext
	private EntityManager entityManagerLocation;
	
	@Override
	public void save(Location entity) {
		entityManagerLocation.persist(entity);		
		
	}

	@Override
	public void update(Location entity) {
		entityManagerLocation.merge(entity);		
		
	}

	@Override
	public void delete(Location  entity) {
		entityManagerLocation.remove(entity);		
		
	}

	@Override
	public Optional<Location>findById(Integer  id) {
		return Optional.ofNullable(entityManagerLocation.find(Location.class, id));		
	}
	
	@Override
	public List<Location> findAll() {
		String jpql = "Select l from Location l";
		return 	entityManagerLocation.createQuery(jpql).getResultList();	
	}
}
