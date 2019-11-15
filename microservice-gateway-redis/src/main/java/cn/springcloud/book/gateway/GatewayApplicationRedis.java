package cn.springcloud.book.gateway;

import cn.springcloud.book.gateway.filter.HoleBodyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.handler.predicate.ReadBodyPredicateFactory;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplicationRedis {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplicationRedis.class, args);
	}

	@Bean
	public InMemoryRouteDefinitionRepository routeDefinitionRepository() {
		return new InMemoryRouteDefinitionRepository();
	}

	@Bean
	public HoleBodyFilter holeBodyFilter(){
		return new HoleBodyFilter();
	}

}
