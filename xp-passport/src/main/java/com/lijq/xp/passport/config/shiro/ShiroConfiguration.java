package com.lijq.xp.passport.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro 权限配置
 *
 * @author Lijq
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public SecurityManager securityManager() {
        SecurityManager securityManager = new DefaultWebSecurityManager();
        // TODO  need some properties
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);




        return shiroFilterFactoryBean;
    }
}
