package com.localaihub.platform.module.infra.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class InfraServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfraServerApplication.class, args);
    }

    @RestController
    static class TestController {

        @GetMapping("/")
        public String get() {
            return "test";
        }

        @GetMapping("/echo")
        public String echo() {
            return "provider:";
        }

    }
}
