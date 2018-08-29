package com.lijq.xp.portal;

import com.lijq.xp.rule.RuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Lijq
 */
@EnableZuulProxy
@SpringBootApplication
@RibbonClient(name = "xp-user-server", configuration = RuleConfig.class)
public class XpPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(XpPortalApplication.class, args);
    }
}
