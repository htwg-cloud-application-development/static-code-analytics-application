package de.htwg.konstanz.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
// To auto register microservice with Eureka
@EnableDiscoveryClient
public class MoodleServiceApplication { //NOPMD


    /**
     * Main method for starting the moodle microservice
     * @param args for loading configuration
     */
    public static void main(String... args) {
        // start the microservice
        SpringApplication.run(MoodleServiceApplication.class, args);
    }
}
