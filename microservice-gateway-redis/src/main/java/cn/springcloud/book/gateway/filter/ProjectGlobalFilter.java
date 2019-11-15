package cn.springcloud.book.gateway.filter;

import cn.springcloud.book.gateway.utils.RedisUtil;
import cn.springcloud.book.model.TUser;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;


/**
 * 全局过滤器
 */
@Component
public class ProjectGlobalFilter implements GlobalFilter,Ordered {

    private Logger log = LoggerFactory.getLogger(ProjectGlobalFilter.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int getOrder() {
        //filter执行的优先级,值越小则优先级越大
        return 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("【】ProjectGlobalFilter。。。。。。。。。。。。。");
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        //从Redis获取用户信息
        String userStr = (String) redisUtil.get("name");
        TUser user = JSONObject.parseObject(userStr,TUser.class);


        //redisString.setKey("nameName","zhangsan");
        //System.out.println("name:"+redisString.getValue("nameName"));

        //DefaultServerRequest req = new DefaultServerRequest( exchange );




        /*ServerHttpRequestDecorator serverHttpRequestDecorator = new ServerHttpRequestDecorator(request){
            @Override
            public Flux<DataBuffer> getBody() {
                Flux<DataBuffer> body = super.getBody();


                AtomicReference<String> bodyRef = new AtomicReference<>();//缓存读取的request body信息
                body.subscribe(dataBuffer -> {
                    CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
                    DataBufferUtils.release(dataBuffer);
                    bodyRef.set(charBuffer.toString());
                });//读取request body到缓存
                *//*DefaultServerRequest req = new DefaultServerRequest( exchange );
                System.out.println("body22:"+getRequstBody(req));*//*
                String bodyStr = bodyRef.get();//获取request body
                System.out.println("bodyStr:"+bodyStr);//这里是我们需要做的操作

                JSONObject jsonObject = JSON.parseObject(bodyStr);
                System.out.println("bodyStrJson:"+jsonObject);
                RequestMessage requestMessage = JSONObject.toJavaObject(jsonObject,RequestMessage.class);

                DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
                Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

                return bodyFlux;
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
        };*/
        //return chain.filter(exchange.mutate().request(serverHttpRequestDecorator).build());
        return chain.filter(exchange);
    }

    /**网关请求响应*/
    /*private Mono<Void> gatewayResponse(String code, String message, ServerWebExchange exchange) {
        // 若验证不成功，返回提示信息
        ServerHttpResponse response = exchange.getResponse();
        //BaseRespVo<T> baseRespVo = ResponseUtils.responseMsg( code, message, null );
        //byte[] bits = JsonUtil.toJson( baseRespVo ).getBytes( StandardCharsets.UTF_8 );
        String bits = "";
        DataBuffer buffer = response.bufferFactory().wrap(bits.getBytes());
        response.setStatusCode( HttpStatus.UNAUTHORIZED );
        // 指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add( "Content-Type", "text/plain;charset=UTF-8" );
        return response.writeWith( Mono.just( buffer ) );
    }*/

    /*private DataBuffer stringBuffer(String value){
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }*/

    /**
     * 获取requestBody
     * 这个方法可能需要优化
     *
     * @param serverRequest
     * @return
     */
   /* private String getRequstBody(ServerRequest serverRequest) {
        StringBuilder stringBuilder = new StringBuilder();
        serverRequest.bodyToMono(String.class).subscribe(s -> {
            stringBuilder.append(s);
        });
        return stringBuilder.toString();
    }*/

}
