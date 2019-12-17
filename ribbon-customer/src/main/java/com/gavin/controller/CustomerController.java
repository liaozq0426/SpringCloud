package com.gavin.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CustomerController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient client;
	
	@Autowired
	private HelloService helloService;
	
	
	@Autowired
	private Registration registration;
	
	@GetMapping("ribbon-consumer")
	public String helloConsumer() {
		String serviceId = registration.getServiceId();
		logger.info("serviceId:" + serviceId);
		List<ServiceInstance> instances = client.getInstances(serviceId);
		for(ServiceInstance instance : instances) {
			logger.info("host:" + instance.getHost() + ",port:" + instance.getPort());
		}
		return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
	}
	
	
	@GetMapping("ribbon-hystrix-consumer")
	public String hystrixConsumer() {
		return helloService.helloService();
	}
}
