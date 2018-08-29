[TOC]

# X-PEAS
> 基于Spring Boot, Spring 构建的微服务项目案例。

# Reference

- [Spring Boot 2.0.4.RELEASE ](https://docs.spring.io/spring-boot/docs/2.0.4.RELEASE/reference/htmlsingle)

- [Spring Cloud Finchley.SR1](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/single/spring-cloud.html)

# Modules

## xp-portal

> 访问门户

### 路由配置

1. pom.xml

   ``` xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
   </dependency>
   ```

2. 开启配置

   ``` java
   @EnableZuulProxy
   ```

3. 配置文件

   ``` yaml
   # zuul.routes.${app-name} = /${app-url-prefix}/**
   zuul:
     ignored-services: "*"
     routes:
       xp-user-provider:
         path: /api/**
         serviceId: xp-user-provider
   ```


## xp-user-provider

> 用户服务







# Dependency 依赖

## Eureka

>

## Ribbon

> 基于`客户端`的`负载均衡`组件

## Feign

## Hystrix

## Zuul

## Config





# License

X-peas is Open Source software released under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html).