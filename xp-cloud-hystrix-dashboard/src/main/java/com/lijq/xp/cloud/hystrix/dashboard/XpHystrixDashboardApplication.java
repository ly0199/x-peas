package com.lijq.xp.cloud.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author Lijq
 */
@SpringBootApplication
@EnableHystrixDashboard
public class XpHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(XpHystrixDashboardApplication.class, args);
    }
}
