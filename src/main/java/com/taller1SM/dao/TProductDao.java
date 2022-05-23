package com.taller1SM.dao;

import java.util.List;
import java.util.Optional;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productmodel;
import com.taller1SM.model.prod.Productsubcategory;

@Repository
@Scope("singleton")
public class TProductDao implements IProductDao {

	@PersistenceContext
	private EntityManager entityManagerProduct;

	@Override
	public void save(Product entity) {
		entityManagerProduct.merge(entity);
		System.out.println("guardado en el dao bd");
	}

	@Override
	public void update(Product entity) {
		entityManagerProduct.merge(entity);
		System.out.println("modificado con dao bd");
	}

	@Override
	public void delete(Product entity) {
		entityManagerProduct.remove(entity);
		System.out.println("Eliminado con dao bd");
	}

	@Override
	public Optional<Product> findById(Integer productid) {
		return Optional.ofNullable(entityManagerProduct.find(Product.class, productid));
	}

	@Override
	public List<Product> findAll() {
		String jpql = "Select a from Product a";
		return entityManagerProduct.createQuery(jpql).getResultList();
	}

	@Override
	public List<Product> findByproductModel(Integer modelid) {
		String jpql = "Select a from Product a where a.productmodel.productmodelid= :modelid";
		TypedQuery<Product> query = entityManagerProduct.createQuery(jpql, Product.class);
		return query.setParameter("modelid", modelid).getResultList();

	}

	@Override
	public List<Product> findBySizeunitmeasure(String idmeasure) {
		String jpql = "SELECT p FROM Product p WHERE p.unitmeasure1.unitmeasurecode= :idmeasure";
		TypedQuery<Product> query = entityManagerProduct.createQuery(jpql, Product.class);
		return query.setParameter("idmeasure", idmeasure).getResultList();
	}

	@Override
	public List<Product> findBySubcategoryid(Integer id) {

		String jpql = "SELECT p FROM Product p WHERE p.productsubcategory.productsubcategoryid= :id";
		TypedQuery<Product> query = entityManagerProduct.createQuery(jpql, Product.class);
		return query.setParameter("id", id).getResultList();
	}

	public List<Object[]> findByProductSumInventory_orderByLocation(Productsubcategory subcategory) {
		String jpql = "SELECT p,COUNT(pir) "
				+ "FROM Product p, Productinventory pir "
				+ "WHERE pir MEMBER OF p.productinventories "
				+ "AND pir.product.productsubcategory = :subcategory "
				+ "AND pir.quantity >= 1 "
				+ "ORDER BY pir.location";
		
		TypedQuery<Object[]> query = entityManagerProduct.createQuery(jpql, Object[].class);
		return 	query.setParameter("subcategory", subcategory).getResultList();

	}

	public List<Product> findByProductCostHistoric() {
		String jpql = "SELECT pro "
				+ "FROM Product pro "
				+ "WHERE size (pro.productcosthistories)>=2";
		
		
		TypedQuery<Product> query2 = entityManagerProduct.createQuery(jpql, Product.class);
		return query2.getResultList();
	
	}



}

//Permita que los productos puedan buscarse por el id de la subcategoría, el modelo del producto y el código de la unidad de medida de tamaño independientemente.
