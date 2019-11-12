package cn.springcloud.book.interceptor;

import feign.RequestInterceptor;
import feign.Retryer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FeignConfiguration {
    private Logger logger = LoggerFactory.getLogger(FeignConfiguration.class);
    public static int connectTimeOutMillis = 90000;//超时时间
    public static int readTimeOutMillis = 90000;

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Retryer feignRetryer() {
        Retryer retryer = new Retryer.Default(100, 1000, 4);
        return retryer;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = (requestTemplate) -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            try {
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        requestTemplate.header(name, values);
                    }
                }
                logger.info("接口路径："+request.getRequestURL().toString());
                logger.info("服务名称："+request.getServerPort());

                StringBuffer body = new StringBuffer();
                Enumeration<String> bodyNames = request.getParameterNames();
                if (bodyNames != null) {
                    Map map=new HashMap();
                    while (bodyNames.hasMoreElements()) {
                        String name = bodyNames.nextElement();
                        String values = request.getParameter(name);
                        requestTemplate.query(name, values);
                        map.put(name,values);
                    }
                    logger.info("传入参数："+map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return requestInterceptor;
    }
}
