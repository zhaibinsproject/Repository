package cn.springcloud.book.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class HoleBodyFilter implements GatewayFilter,Ordered {

    private Logger log = LoggerFactory.getLogger(ProjectGlobalFilter.class);

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("【】HoleBodyFilter。。。。。。。。。。。。。");

        Object object = exchange.getAttribute("cachedRequestBodyObject");
        System.out.println("cachedObject:"+object);

        return chain.filter(exchange);
    }



}
