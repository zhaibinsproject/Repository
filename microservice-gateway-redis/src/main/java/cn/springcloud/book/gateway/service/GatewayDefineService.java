package cn.springcloud.book.gateway.service;

import cn.springcloud.book.gateway.model.GatewayDefine;

import java.util.List;

public interface GatewayDefineService {

    String loadRouteDefinition() throws Exception;

    GatewayDefine save(GatewayDefine gatewayDefine) throws Exception;

    void deleteById(String id) throws Exception;

}
