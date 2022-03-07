package com.eventdriven.estore.ProductsService.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.eventdriven.estore.ProductsService.core.data.ProductEntity;
import com.eventdriven.estore.ProductsService.core.data.ProductsRepository;
import com.eventdriven.estore.ProductsService.query.rest.ProductRestModel;

@Component
public class ProductsQueryHandler {

	private final ProductsRepository productsRepository;

	public ProductsQueryHandler(ProductsRepository productsRepository) {
		this.productsRepository = productsRepository;
	}
	
	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductsQuery query) {
		
		List<ProductRestModel> productsRest = new ArrayList<>();
		
		List<ProductEntity> storedProducts = productsRepository.findAll();
		
		for (ProductEntity p : storedProducts) {
			ProductRestModel productRestModel = new ProductRestModel();
			BeanUtils.copyProperties(p, productRestModel);
			productsRest.add(productRestModel);
		}
		
		return productsRest;
	}
}
