package com.localaihub.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiDatasetsPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiDatasetsPlatformApplication.class, args);
        System.out.println("http://127.0.0.1:8088");
    }

}
