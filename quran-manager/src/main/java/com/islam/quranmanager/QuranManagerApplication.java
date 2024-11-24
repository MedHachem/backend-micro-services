package com.islam.quranmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QuranManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuranManagerApplication.class, args);
    }

}
