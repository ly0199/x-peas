[TOC]

> 1. [Spring Cloud版本 Finchley.SR1](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/single/spring-cloud.html)
> 2. 项目中使用的组件

# Spring Cloud 部分组件最简使用

## Springloaded

``` xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>springloaded</artifactId>
    <version>1.2.8.RELEASE</version>
    <scope>provided</scope>
</dependency>
```



## Eureka

> `server` ：服务注册中心
>
> `client` ：服务发现

### Eureka Server

1. pom.xml

   ``` xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   </dependency>
   ```

2. application.yml

   ``` yam
   eureka:
     server:
       enable-self-preservation: false # 取消自我保护机制
       eviction-interval-timer-in-ms: 30000 # 服务失效驱逐时间
     instance:
       hostname: localhost
     client:
       registerWithEureka: false # 取消自我注册
       fetchRegistry: false # 自己注册自己不需要检索
       serviceUrl:
         defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
   ```

3. Application.java

   ``` java
   package com.lijq.xp.cloud.eureka;
   
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
   
   /**
    * @author Lijq
    */
   @SpringBootApplication
   @EnableEurekaServer
   public class XpCloudEurekaServerApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(XpCloudEurekaServerApplication.class, args);
       }
   }
   ```


### Eureka Client

1. pom.xml

   ``` xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   ```

2. application.yml

   ``` yaml
   eureka:
     client:
       service-url:
          defaultZone: http://localhost:8761/eureka/
     instance:
       instance-id: ${spring.application.name}.${server.port}
       prefer-ip-address: true
   ```

3. Application.java

   ``` java
   package com.lijq.xp.user.server;
   
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
   import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
   import org.springframework.cloud.netflix.hystrix.EnableHystrix;
   
   /**
    * @author Lijq
    */
   @SpringBootApplication
   @EnableDiscoveryClient
   @EnableEurekaClient
   public class XpUserServerApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(XpUserServerApplication.class, args);
       }
   }
   ```

4. 注意

   > 1. yml文件中 `eureka.instance.instance-id: ${spring.application.name}.${server.port}`的加`${server.port}`的目的在于区分同一服务的不同实例，否则注册到`eureka-server`中时会出现问题
   > 2. 使用idea开发时，同一个服务启动不同的实例需在`Edit Configurations`中修改启动参数利用`--spring.profiles.active=xx`或`server.port=xx`等方式简易实现

## Ribbon

> 基于`客户端`的`负载均衡`组件

1. pom.xml

   ``` xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
   </dependency>	
   ```

2. application.yml

   ``` yaml
   # 最简模式不需要配置
   ```

3. Application.java

   ```java
   package com.lijq.xp.portal.conf;
   
   import org.springframework.cloud.client.loadbalancer.LoadBalanced;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.client.RestTemplate;
   
   /**
    * @author Lijq
    */
   @Configuration
   public class BeanConfig {
   
       @LoadBalanced // 配置使用LB
       @Bean
       public RestTemplate restTemplate() {
           return new RestTemplate();
       }
   }
   ```

4. IRule 

   > 负载均衡算法 `com.netflix.loadbalancer.IRule`

   - `com.netflix.loadbalancer.RoundRobinRule` 轮询
   - `com.netflix.loadbalancer.RandomRule` 随机

   - `com.netflix.loadbalancer.BestAvailableRule` 先过滤掉由于多次访问故障处于短路的服务，选择一个并发量最小的服务进行访问
   - `com.netflix.loadbalancer.AvailabilityFilteringRule` 过滤掉一部分多次故障、并发数超过阀值，剩下按照轮询策略进行访问
   - `com.netflix.loadbalancer.RetryRule` 先按照`RoundRobinRule`策略，如果获取服务失败在指定时间内会进行重试来获取可用服务
   - `com.netflix.loadbalancer.WeightedResponseTimeRule`  根据平均响应时间计算服务权重，响应时间越快，服务权重越大，被选中的概率越高，刚启动的会按照`RoundRobinRule`策略，待统计信息足够时会切换到`WeightedResponseTimeRule`
   - `com.netflix.loadbalancer.ZoneAvoidanceRule` 默认规则，复合判断server所在区域的性能和可用性

   5. 自定义`IRule`

      > 1. 自定义的`IRule`在`@ComponentScan`扫码包下时会被所有Ribbon客户端共享
      > 2. 使用注解`@RibbonClinet(name="${spring.application.name}", configuration=XXRule.class)`

      代码示例

      ``` java
      @Configuration
      @RibbonClient(name = "custom", configuration = CustomConfiguration.class)
      public class TestConfiguration {
      }
      ```

      自定义IRule代码

      ``` java
      package com.lijq.xp.rule;
      
      import com.netflix.client.config.IClientConfig;
      import com.netflix.loadbalancer.AbstractLoadBalancerRule;
      import com.netflix.loadbalancer.ILoadBalancer;
      import com.netflix.loadbalancer.Server;
      import org.slf4j.Logger;
      import org.slf4j.LoggerFactory;
      
      import java.util.List;
      
      /**
       * 自定义IRule实现
       * 目标-> 每一个可用的服务调用3次之后再使用另外的服务
       *
       * @author Lijq
       */
      public class XpRule extends AbstractLoadBalancerRule {
      
          private static Logger log = LoggerFactory.getLogger(XpRule.class);
      
          @Override
          public void initWithNiwsConfig(IClientConfig clientConfig) {
          }
      
          private static int maxTotal = 3;
          private int total = 0;
          private int currentIndex = 0;
      
          @Override
          public Server choose(Object key) {
              return choose(getLoadBalancer(), key);
          }
      
          private Server choose(ILoadBalancer lb, Object key) {
      
              if (lb == null) {
                  log.warn("no load balancer");
                  return null;
              }
      
              Server server = null;
              int count = 0;
              while (server == null && count++ < 10) {
                  List<Server> reachableServers = lb.getReachableServers();
                  List<Server> allServers = lb.getAllServers();
                  int upCount = reachableServers.size();
                  int serverCount = allServers.size();
      
                  if ((upCount == 0) || (serverCount == 0)) {
                      log.warn("No up servers available from load balancer: " + lb);
                      return null;
                  }
      
      
                  if (total < maxTotal) {
                      server = reachableServers.get(currentIndex);
                      total++;
                  } else {
                      total = 0;
                      currentIndex++;
                      if (currentIndex >= reachableServers.size()) {
                          currentIndex = 0;
                      }
                  }
      
                  if (server == null) {
                      /* Transient. */
                      Thread.yield();
                      continue;
                  }
      
                  if (server.isAlive() && (server.isReadyToServe())) {
                      return (server);
                  }
      
                  // Next.
                  server = null;
              }
      
              return server;
          }
      }
      
      ```


## Feign

## Hystrix

### Hystrix Dashboard

## Zuul

> Gateway 路由

1. pom.xml

   ``` xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
   </dependency>
   ```

2. application.yml

   ``` yaml
   # zuul.routes.${app-name} = /${app-url-prefix}/**
   zuul:
     ignored-services: "*"
     host:
       connect-timeout-millis: 10000
       socket-timeout-millis: 60000
     routes:
       xp-user-provider:
         path: /api/**
         serviceId: xp-user-provider
   ```

3. Application.java

   ``` java
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
   ```


## Config

