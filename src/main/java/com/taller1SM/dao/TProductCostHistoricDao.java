package com.taller1SM.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.taller1SM.model.prod.Productcosthistory;

@Repository
@Scope("singleton")
public class TProductCostHistoricDao implements IProductCostHistoric {

	
	

	@PersistenceContext
	private EntityManager entityManagerProductPHC;
	
	@Override
	public void save(Productcosthistory entity) {
		entityManagerProductPHC.persist(entity);		
		
	}

	@Override
	public void update(Productcosthistory entity) {
		entityManagerProductPHC.merge(entity);		
		
	}

	@Override
	public void delete(Productcosthistory entity) {
		entityManagerProductPHC.remove(entity);		
		
	}

	@Override
	public Optional <Productcosthistory> findById(Integer id) {
		return Optional.ofNullable(entityManagerProductPHC.find(Productcosthistory.class, id));		
	}
	
	
	@Override
	public List<Productcosthistory> findByProduct(Integer idproduct) {
		String jpql = "SELECT phc FROM Productcosthistory phc WHERE phc.product.productid= :idproduct";
		TypedQuery<Productcosthistory> query = entityManagerProductPHC.createQuery(jpql, Productcosthistory.class);
		return 	query.setParameter("idproduct", idproduct).getResultList();	
	}

	@Override
	public List<Productcosthistory> findByStandardCost(BigDecimal standardCost) {
		String jpql = "SELECT phc FROM Productcosthistory phc WHERE phc.standardcost= :standardCost";
		TypedQuery<Productcosthistory> query = entityManagerProductPHC.createQuery(jpql, Productcosthistory.class);
		return 	query.setParameter("standardCost", standardCost).getResultList();
	}

	public List<Productcosthistory> findAll() {
		Query query = entityManagerProductPHC.createQuery("SELECT phc FROM Productcosthistory phc");
		return query.getResultList();
	}
	
	//Permita que los costos históricos de productos puedan buscarse el id del producto y el costo estándar de forma independiente.
}
