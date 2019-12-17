package com.gavin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class HelloService {
	
	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="helloFallback",commandProperties= {
		@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds" , value="2000")
	})
	public String helloService() {
		return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
	}
	
	public String helloFallback() {
		return "error";
	}
	
}
