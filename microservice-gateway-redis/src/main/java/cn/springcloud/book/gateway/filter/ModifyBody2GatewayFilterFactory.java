package cn.springcloud.book.gateway.filter;

import cn.springcloud.book.gateway.util.ModifyServerWebExchangeDecorator;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class ModifyBody2GatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            //用serverExchange包装类替换SerberWebExchange，以便后续可以进行扩展操作
            ModifyServerWebExchangeDecorator exchangeDecorator = new ModifyServerWebExchangeDecorator(exchange);
            return chain.filter(exchangeDecorator);
        });
    }
}
