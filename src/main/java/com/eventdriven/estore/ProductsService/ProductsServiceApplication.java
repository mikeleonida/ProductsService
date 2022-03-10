package com.eventdriven.estore.ProductsService;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

import com.eventdriven.estore.ProductsService.command.interceptors.CreateProductCommandInterceptor;
import com.eventdriven.estore.ProductsService.core.errorhandling.ProductsServiceEventsErrorHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler("product-group", conf -> new ProductsServiceEventsErrorHandler());

//		config.registerListenerInvocationErrorHandler(
//				"product-group", 
//				conf -> PropagatingErrorHandler.instance());
	}

	@Bean(name = "productSnapshotTriggerDefinition")
	public SnapshotTriggerDefinition productSnapshotTriggerDefinition(Snapshotter snapshotter) {
		return new EventCountSnapshotTriggerDefinition(snapshotter, 12);
	}

//	@Bean(name="xStream")
//    XStream xStream() {
//        XStream xstream = new XStream();
//        // clear out existing permissions and set own ones
//        xstream.addPermission(NoTypePermission.NONE);
//        // allow any type from the same package
//        xstream.allowTypesByWildcard(new String[] {
//                "com.appsdeveloperblog.estore.core.**",
//                "com.eventdriven.estore.**",
//                "org.axonframework.**",
//                "java.**",
//                "com.thoughtworks.xstream.**"
//        });
//
//        return xstream;
//    }

//    @Bean
//    @Primary
//    public Serializer serializer(XStream xStream) {
//        return XStreamSerializer.builder().xStream(xStream).build();
//    }

//	@Bean(name="productXStreamDefinition")
//	public XStream xstreamDefinition() {
//		XStream xstream = new XStream();
//		// XStream.setupDefaultSecurity(xstream);
//		
//		// allow any type from core package
//		xstream.allowTypesByWildcard(new String[] {
//		    "com.appsdeveloperblog.estore.core.**"
//		});
//		return xstream;
//	}
}
