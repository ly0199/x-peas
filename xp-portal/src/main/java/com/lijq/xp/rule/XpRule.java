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
