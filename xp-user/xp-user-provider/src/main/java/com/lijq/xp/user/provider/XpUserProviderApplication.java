package com.lijq.xp.user.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Lijq
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class XpUserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(XpUserProviderApplication.class, args);
    }
}
