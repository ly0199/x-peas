package com.lijq.xp.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Lijq
 */
@SpringBootApplication
@EnableEurekaServer
public class XpCloudEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(XpCloudEurekaApplication.class, args);
    }
}
