package com.eventdriven.estore.ProductsService;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

import com.eventdriven.estore.ProductsService.command.interceptors.CreateProductCommandInterceptor;
import com.eventdriven.estore.ProductsService.core.errorhandling.ProductsServiceEventsErrorHandler;


@EnableDiscoveryClient
@SpringBootApplication
public class ProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context,
			CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}
	
	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler(
				"product-group", 
				conf -> new ProductsServiceEventsErrorHandler());
		
//		config.registerListenerInvocationErrorHandler(
//				"product-group", 
//				conf -> PropagatingErrorHandler.instance());
	}
}
