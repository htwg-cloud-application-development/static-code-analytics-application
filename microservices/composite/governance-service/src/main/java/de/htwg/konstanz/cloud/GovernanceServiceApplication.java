package de.htwg.konstanz.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Governance microservice used for providing access from frontend
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class GovernanceServiceApplication {

    /**
     * Main method for starting microservice
     * @param args for configuration
     */
    public static void main(String... args) {
        SpringApplication.run(GovernanceServiceApplication.class, args);
    }

    /**
     * Method for returning swagger ui
     * @return swagger ui
     */
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("validate")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Gouvermance Service")
                .description("Handle service calls for gouvermance services")
                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
}
