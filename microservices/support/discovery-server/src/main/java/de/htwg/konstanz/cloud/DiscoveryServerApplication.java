package de.htwg.konstanz.cloud;

import com.netflix.appinfo.AmazonInfo;
import config.EurekaInstanceConfigBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServerApplication {//NOPMD

    public static void main(String... args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }

    @Bean
    @Profile("aws")
    public EurekaInstanceConfigBean eurekaInstanceConfig() {
        EurekaInstanceConfigBean b = new EurekaInstanceConfigBean();
        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        b.setDataCenterInfo(info);
        return b;
    }
}
