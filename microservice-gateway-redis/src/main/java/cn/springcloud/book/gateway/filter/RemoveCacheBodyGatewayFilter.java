package cn.springcloud.book.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class RemoveCacheBodyGatewayFilter implements GatewayFilter,Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).doFinally(s -> exchange.getAttributes().remove("CACHE_GATEWAY_CONTEXT"));
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
