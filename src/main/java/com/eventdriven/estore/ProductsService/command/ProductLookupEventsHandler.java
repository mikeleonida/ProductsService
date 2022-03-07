package com.eventdriven.estore.ProductsService.command;

import org.springframework.stereotype.Component;

import com.eventdriven.estore.ProductsService.core.data.ProductLookupEntity;
import com.eventdriven.estore.ProductsService.core.data.ProductLookupRepository;
import com.eventdriven.estore.ProductsService.core.events.ProductCreatedEvent;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {
	
	private final ProductLookupRepository productLookupRepository;
	
	public ProductLookupEventsHandler(ProductLookupRepository p) {
		this.productLookupRepository = p;
	}
	
	@EventHandler
	public void on(ProductCreatedEvent event) {
		
		ProductLookupEntity productLookupEntity = new ProductLookupEntity(
				event.getProductId(), event.getTitle());
				
		productLookupRepository.save(productLookupEntity);
	}

}
