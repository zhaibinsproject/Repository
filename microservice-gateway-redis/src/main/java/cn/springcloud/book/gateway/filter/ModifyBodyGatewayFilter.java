package cn.springcloud.book.gateway.filter;

import cn.springcloud.book.gateway.model.RequestMessage;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;

public class ModifyBodyGatewayFilter implements GatewayFilter,Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String path = uri.getPath();
        //log.info("拦截的uri [{}]",path);
        HttpHeaders headers = request.getHeaders();
        //获取token请求头
        //String token = headers.getFirst(BaseConstants.HEAD_AUTHORIZATION);
        ServerHttpResponse response = exchange.getResponse();
        System.out.println("hello-----------------------------------------");
        if(true) {
            ServerHttpRequestDecorator serverHttpRequestDecorator = new ServerHttpRequestDecorator(request){

                @Override
                public Flux<DataBuffer> getBody() {
                    Flux<DataBuffer> body = super.getBody();
                    return body.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        //这个就是request body的json格式数据
                        String bodyJson = new String(content, Charset.forName("UTF-8"));
                        //转化成json对象
                        //JSONObject jsonObject = JSON.parseObject(bodyJson);
                        RequestMessage requestMessage = JSONObject.toJavaObject(JSONObject.parseObject(bodyJson),RequestMessage.class);
                        requestMessage.getCommonInfo().setUserCode("321");


                        //把用户id和客户端id添加到json对象中
                        //String result = jsonObject.toJSONString();
                        String result = JSONObject.toJSONString(requestMessage);
                        //转成字节
                        byte[] bytes = result.getBytes();

                        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
                        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
                        buffer.write(bytes);
                        return buffer;
                    });
                }
                //复写getHeaders方法，删除content-length
                @Override
                public HttpHeaders getHeaders() {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.putAll(super.getHeaders());
                    //由于修改了请求体的body，导致content-length长度不确定，因此使用分块编码
                    httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                    return httpHeaders;
                }


            };
            //log.info("验证token通过。");
            return chain.filter(exchange.mutate().request(serverHttpRequestDecorator).build());
        }else{//拦截
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }


}
