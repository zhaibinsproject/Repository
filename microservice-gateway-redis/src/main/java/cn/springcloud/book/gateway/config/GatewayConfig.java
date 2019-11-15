package cn.springcloud.book.gateway.config;

import cn.springcloud.book.gateway.filter.HoleBodyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableAutoConfiguration
public class GatewayConfig {

    private Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

    @Autowired
    public HoleBodyFilter holeBodyFilter;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        RouteLocatorBuilder.Builder serviceProvider = routes
                /**
                 * 路由配置
                 */
                .route("gateway-sample",
                        r -> r.readBody(Object.class, readBody -> {
                            logger.info("request method POST, Content-Type is application/json, body  is:{}", readBody);
                            return true;
                        }).and().path("/test/**").filters(f->{f.filter(holeBodyFilter);return f;})
                                .uri("lb://microservice-provider-user"));

        RouteLocator routeLocator = serviceProvider.build();
        return routeLocator;
    }

}
