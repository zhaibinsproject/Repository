package cn.springcloud.book.gateway.filter;

import cn.springcloud.book.gateway.model.RequestMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 全局过滤器
 */
@Component
public class ProjectGlobalTwoFilter implements GlobalFilter,Ordered {

    private Logger log = LoggerFactory.getLogger(ProjectGlobalTwoFilter.class);

    @Override
    public int getOrder() {
        //filter执行的优先级,值越小则优先级越大
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*log.info("【】ProjectGlobalFilter。。。。。。。。。。。。。");
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();


        URI requestUri = request.getURI();
        String method=exchange.getRequest().getMethodValue();
        //开始 时间

        HttpHeaders headers=exchange.getRequest().getHeaders();
        String contentType = headers.getFirst("Content-Type");

        URI ex = UriComponentsBuilder.fromUri(requestUri).build(true).toUri();
        ServerHttpRequest newRequest = request.mutate().uri(ex).build();

        String str = getRequstBody(exchange);
        System.out.println("str:"+str);
        DataBuffer bodyDataBuffer = stringBuffer(str);
        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
        newRequest = new ServerHttpRequestDecorator(newRequest) {
            @Override
            public Flux<DataBuffer> getBody() {
                return bodyFlux;
            }
        };
        return chain.filter(exchange.mutate().request(newRequest).build());*/
        return chain.filter(exchange);
    }

    private DataBuffer stringBuffer(String value){
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    /**
     * 获取requestBody
     * 这个方法可能需要优化
     * @param exchange
     * @return
     */
    private String getRequstBody(ServerWebExchange exchange) {
        ServerRequest serverRequest = new DefaultServerRequest(exchange);
        StringBuilder stringBuilder = new StringBuilder();
        /*serverRequest.bodyToMono(String.class).subscribe(s -> {
            stringBuilder.append(s);
        });*/
        serverRequest.bodyToMono(String.class).map(str -> {
            stringBuilder.append(str);
            return str;
        });
        return stringBuilder.toString();
    }

}
