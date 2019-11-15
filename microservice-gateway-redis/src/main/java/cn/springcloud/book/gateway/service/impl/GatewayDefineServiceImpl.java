package cn.springcloud.book.gateway.service.impl;

import cn.springcloud.book.gateway.model.GatewayDefine;
import cn.springcloud.book.gateway.repository.GatewayDefineRepository;
import cn.springcloud.book.gateway.service.GatewayDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Service
public class GatewayDefineServiceImpl implements GatewayDefineService {
    @Autowired
    private InMemoryRouteDefinitionRepository routeDefinitionRepository;
    @Autowired
    private GatewayDefineRepository gatewayDefineRepository;

    /**
     * 加载数据库重路由信息到内存
     * @return
     * @throws Exception
     */
    @Override
    public String loadRouteDefinition() throws Exception {
        try {
            //将数据库路由信息加载到内存中
            List<GatewayDefine> gatewayDefinesList = gatewayDefineRepository.findAll();

            for (GatewayDefine gatewayDefine: gatewayDefinesList) {
                RouteDefinition definition = assembleRouteDefinition(gatewayDefine);
                //保存信息到内存
                routeDefinitionRepository.save(Mono.just(definition)).subscribe();
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }

    /**
     * 添加路由信息
     * @param gatewayDefine
     * @return
     * @throws Exception
     */
    @Override
    public GatewayDefine save(GatewayDefine gatewayDefine) throws Exception {
        //添加到数据库中
        GatewayDefine reDfine = gatewayDefineRepository.save(gatewayDefine);
        //直接添加到内存中
        RouteDefinition routeDefinition = assembleRouteDefinition(reDfine);
        routeDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
        return reDfine;
    }

    /**
     * 删除路由信息
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteById(String id) throws Exception {
        //删除内存中的路由信息
        routeDefinitionRepository.delete(Mono.just(id));
        //删除数据库中的路由信息
        gatewayDefineRepository.deleteById(id);
    }

    /**
     * 封装成为 RouteDefinition 对象
     * @param gatewayDefine
     * @return
     */
    private RouteDefinition assembleRouteDefinition(GatewayDefine gatewayDefine) {
        RouteDefinition definition = new RouteDefinition();
        // ID
        definition.setId(gatewayDefine.getId());

        definition.setOrder(gatewayDefine.getOrder());

        if(gatewayDefine.getPredicateDefinition().size()>0)
            definition.setPredicates(gatewayDefine.getPredicateDefinition());
        if(gatewayDefine.getFilterDefinition().size()>0)
            definition.setFilters(gatewayDefine.getFilterDefinition());
        // URI
        URI uri = UriComponentsBuilder.fromUriString(gatewayDefine.getUri()).build().toUri();
        definition.setUri(uri);
        return definition;
    }
}
