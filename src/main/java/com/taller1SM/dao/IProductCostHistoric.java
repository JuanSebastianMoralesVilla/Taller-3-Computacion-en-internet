package com.taller1SM.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.taller1SM.model.prod.Productcosthistory;

public interface IProductCostHistoric {

	void save(Productcosthistory entity);

	void update(Productcosthistory entity);

	void delete(Productcosthistory entity);

	Optional<Productcosthistory> findById(Integer productid);

	List<Productcosthistory> findByProduct(Integer idproduct);

	List<Productcosthistory> findByStandardCost(BigDecimal standardCost);

}
