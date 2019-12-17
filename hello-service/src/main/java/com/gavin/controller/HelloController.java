package com.gavin.controller;

import java.util.List;
import java.util.Random;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	@Autowired
    private Registration registration; // 服务注册

	
	@Autowired
	private DiscoveryClient client;
	
	@GetMapping("hello")
	public String index() throws Exception{
		//ServiceInstance instance = serviceInstance();
		//logger.info("/hello , host:" + instance.getHost() + ", port:" + instance.getPort() + " ,service_id:" + instance.getServiceId());
		
		serviceInstance();
		
		// 让处理线程等待几分钟
		int sleepTime = new Random().nextInt(3000);
		logger.info("sleepTime:" + sleepTime);
		Thread.sleep(sleepTime);
		
		
		return "Hello World," + System.currentTimeMillis();
	}
	
	
	public ServiceInstance serviceInstance() {
		String serviceId = registration.getServiceId();
		logger.info("serviceId:" + serviceId);
        List<ServiceInstance> list = client.getInstances(serviceId);
        if (list != null && list.size() > 0) {
            for(ServiceInstance itm : list){
               logger.info("host:" + itm.getHost() + ",port:" + itm.getPort());
            }   
        }
        logger.info("---------------");
        return null;
    }
	
}
