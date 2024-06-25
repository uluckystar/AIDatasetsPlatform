package com.localaihub.platform.module.system.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableAsync
//@EnableSwagger2
public class SystemServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SystemServerApplication.class, args);
	}
}