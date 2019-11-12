package cn.springcloud.book.gateway.config;

import cn.springcloud.book.gateway.filter.HoleBodyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.gateway.handler.predicate.ReadBodyPredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@Configuration
@EnableAutoConfiguration
public class GatewayConfig {

    private Logger log = LoggerFactory.getLogger(GatewayConfig.class);

    @Autowired
    private HoleBodyFilter holeBodyFilter;

    private static final String SERVICE = "/test/**";
    private static final String URI = "http://127.0.0.1:8001/";

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {

        /*
        route1 是get请求，get请求使用readBody会报错
        route2 是post请求，Content-Type是application/x-www-form-urlencoded，readbody为String.class
        route3 是post请求，Content-Type是application/json,readbody为Object.class
         */
        //ReadBodyPredicateFactory;
        RouteLocatorBuilder.Builder routes = builder.routes();
        RouteLocatorBuilder.Builder serviceProvider = routes
                .route("route3",
                        r -> r
                                .readBody(Object.class, readBody -> {
                                    log.info("request method POST, Content-Type is application/json, body  is:{}", readBody);
                                    return true;
                                })
                                .and()
                                .path(SERVICE)
                                .filters(f -> {
                                    f.filter(holeBodyFilter);
                                    return f;
                                })
                                .uri(URI));
        RouteLocator routeLocator = serviceProvider.build();
        log.info("custom RouteLocator is loading ... {}", routeLocator);
        return routeLocator;
    }

}
