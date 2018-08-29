package com.lijq.xp.rule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lijq
 */
@Configuration
public class RuleConfig {

    @Bean
    public IRule iRule() {
        return new XpRule();
    }
}
