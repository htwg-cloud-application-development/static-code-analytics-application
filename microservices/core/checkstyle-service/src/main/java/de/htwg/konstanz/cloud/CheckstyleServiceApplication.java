package de.htwg.konstanz.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class CheckstyleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckstyleServiceApplication.class, args);
    }
}
