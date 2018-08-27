package com.lijq.xp.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Lijq
 */
@EnableZuulProxy
@SpringBootApplication
public class XpPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(XpPortalApplication.class, args);
    }
}
