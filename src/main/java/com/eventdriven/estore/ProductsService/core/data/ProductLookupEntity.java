package com.eventdriven.estore.ProductsService.core.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product_lookup")
public class ProductLookupEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6995868127992456110L;

	@Id
	private String productId;
	
	@Column(unique=true)
	private String title;
	
}
