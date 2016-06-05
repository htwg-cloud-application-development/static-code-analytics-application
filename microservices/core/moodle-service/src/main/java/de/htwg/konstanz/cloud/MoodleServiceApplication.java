package de.htwg.konstanz.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootApplication
// To auto register microservice with Eureka
@EnableDiscoveryClient
@EnableAutoConfiguration
public class MoodleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoodleServiceApplication.class, args);
    }
}
