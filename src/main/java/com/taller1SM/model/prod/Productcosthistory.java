package com.taller1SM.model.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the productcosthistory database table.
 *
 */
@Entity
@NamedQuery(name = "Productcosthistory.findAll", query = "SELECT p FROM Productcosthistory p")
public class Productcosthistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PRODUCTCOSTHISTORY_PRODUCTCOSTHISTORYID_GENERATOR", allocationSize = 1, sequenceName = "PRODUCTCOSTHISTORY_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTCOSTHISTORY_PRODUCTCOSTHISTORYID_GENERATOR")
	private Integer id;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate enddate;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate modifieddate;
	
	@NotNull
	@Min(1)
	private BigDecimal standardcost;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "productid")
	private Product product;

	public Productcosthistory() {
	}

	public LocalDate getEnddate() {
		return this.enddate;
	}

	public Integer getId() {
		return this.id;
	}

	public LocalDate getModifieddate() {
		return this.modifieddate;
	}

	public Product getProduct() {
		return this.product;
	}

	public BigDecimal getStandardcost() {
		return this.standardcost;
	}

	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setModifieddate(LocalDate modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setStandardcost(BigDecimal standardcost) {
		this.standardcost = standardcost;
	}

}