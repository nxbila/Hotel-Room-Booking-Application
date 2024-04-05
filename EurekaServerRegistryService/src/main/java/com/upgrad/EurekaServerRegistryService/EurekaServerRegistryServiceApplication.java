package com.upgrad.EurekaServerRegistryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerRegistryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerRegistryServiceApplication.class, args);
	}

}
