package com.eventdriven.estore.ProductsService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import com.eventdriven.estore.ProductsService.core.data.ProductEntity;
import com.eventdriven.estore.ProductsService.core.data.ProductsRepository;
import com.eventdriven.estore.ProductsService.core.events.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

	private final ProductsRepository productsRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsHandler.class);

	public ProductEventsHandler(ProductsRepository productsRepository) {
		this.productsRepository = productsRepository;
	}
	
	@ExceptionHandler(resultType=Exception.class)
	public void handle(Exception ex) throws Exception {
		// Log error message
		throw ex;
	}
	
	@ExceptionHandler(resultType=IllegalArgumentException.class)
	public void handle(IllegalArgumentException ex) {
		// Log error message
	}

	@EventHandler
	public void on(ProductCreatedEvent event) throws Exception {

		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(event, productEntity);

		try {
			productsRepository.save(productEntity);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		
		//if (true) throw new Exception("Forcing exception in the events handler class");
	}
	
	@EventHandler
	public void on(ProductReservedEvent productReservedEvent) {
		ProductEntity productEntity = productsRepository.findByProductId(productReservedEvent.getProductId());
		productEntity.setQuantity(productEntity.getQuantity()-productReservedEvent.getQuantity());
		productsRepository.save(productEntity);
		
		LOGGER.info("ProductReservedEvent is called for productID: "+ productReservedEvent.getProductId()
				+ " and orderId: " + productReservedEvent.getOrderId());
	}

}
