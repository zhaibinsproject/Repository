package cn.springcloud.book.gateway.filter;

import cn.springcloud.book.gateway.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestTimeGlobalFilter implements GlobalFilter,Ordered {

    private Logger log = LoggerFactory.getLogger(ProjectGlobalFilter.class);
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("【】RequestTimeGlobalFilter。。。。。。。。。。。。。");
        Object object = exchange.getAttribute("cachedRequestBodyObject");
        System.out.println("cachedObject:"+object);





        return chain.filter(exchange);
    }
}
