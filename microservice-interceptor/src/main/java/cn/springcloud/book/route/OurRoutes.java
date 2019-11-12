package cn.springcloud.book.route;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.Server;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OurRoutes extends RandomRule {

    ILoadBalancer balancer = new BaseLoadBalancer();

    @Override
    public Server choose(Object key) {
        List<Server> servers = balancer.getAllServers();
        return super.choose(key);
    }
}
