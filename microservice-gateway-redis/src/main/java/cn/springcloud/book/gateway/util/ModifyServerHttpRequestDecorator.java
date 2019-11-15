package cn.springcloud.book.gateway.util;

import cn.springcloud.book.gateway.filter.ProjectGlobalFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static reactor.core.scheduler.Schedulers.single;

public class ModifyServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private Logger log = LoggerFactory.getLogger(ProjectGlobalFilter.class);
    private final StringWriter cachedCopy = new StringWriter();
    private InputStream dataBuffer;
    private DataBuffer bodyDataBuffer;
    private int getBufferTime = 0;
    private byte[] bytes;

    public ModifyServerHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        if (getBufferTime == 0) {
            getBufferTime++;
            Flux<DataBuffer> flux = super.getBody();
            return flux
                    .publishOn(single())
                    .map(this::cache)
                    .doOnComplete(() -> trace(getDelegate(), cachedCopy.toString()));
        } else {
            return Flux.just(getBodyMore());
        }
    }

    private DataBuffer getBodyMore() {
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
        DataBuffer dataBuffer = nettyDataBufferFactory.wrap(bytes);
        bodyDataBuffer = dataBuffer;
        return bodyDataBuffer;
    }

    private DataBuffer cache(DataBuffer buffer) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            dataBuffer = buffer.asInputStream();
            //bytes = IOUtils.toByteArray(dataBuffer);
            bytes = IOUtils.readBytesAndClose(dataBuffer,-1);
            NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
            DataBuffer dataBuffer = nettyDataBufferFactory.wrap(bytes);
            bodyDataBuffer = dataBuffer;
            return bodyDataBuffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void trace(ServerHttpRequest request, String requestBody) {
        log.info(requestBody);
    }
}
