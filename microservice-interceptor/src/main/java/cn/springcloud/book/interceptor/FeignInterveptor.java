package cn.springcloud.book.interceptor;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FeignInterveptor implements RequestInterceptor {
    Logger logger = LoggerFactory.getLogger(FeignInterveptor.class);
    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("【interceptor】。。。。。。。。。。。。。。。。。。。。");
        Request request = requestTemplate.request();
        //logger.info("【body】:"+request.requestBody().asString());
    }
}
