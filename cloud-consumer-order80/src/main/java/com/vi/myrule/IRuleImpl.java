package com.vi.myrule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 继承AbstractLoadBalancerRule类，实现一个自定义的负载均衡
 */
@Slf4j
public class IRuleImpl extends RandomRule {
    private static AtomicInteger count = new AtomicInteger(0);

    public Server choose(ILoadBalancer lb, Object key) {
        log.info("****调用自定义的轮询方法");
        if (lb == null) {
            return null;
        }
        Server server = null;

        List<Server> allList = lb.getAllServers();

        int serverCount = allList.size();
        if (serverCount == 0) {
            return null;
        }
        int index = getAndIncrement(serverCount);
        server = allList.get(index);
        return server;

    }

    //获取服务下标
    private int getAndIncrement(int serverCount) {
        int current;
        int next;
        do {
             current = count.get();
             next = current >= Integer.MAX_VALUE ? 0 : current + 1;
            log.info("****第" + next + "次访问");
        } while(! count.compareAndSet(current,next));

        return count.get() % serverCount;
    }
}
