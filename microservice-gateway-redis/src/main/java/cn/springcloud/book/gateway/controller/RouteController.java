package cn.springcloud.book.gateway.controller;

import cn.springcloud.book.gateway.model.GatewayDefine;
import cn.springcloud.book.gateway.service.GatewayDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    @Autowired
    private GatewayDefineService gatewayDefineService;


    @RequestMapping("/saveRoute")
    public GatewayDefine save(@RequestBody GatewayDefine gatewayDefine){
        try{
            return this.gatewayDefineService.save(gatewayDefine);
        }catch (Exception e){
            e.printStackTrace();
            return new GatewayDefine();
        }

    }

    @RequestMapping("/deleteRoute")
    public boolean deleteRoute(@RequestBody String id){
        try{
            this.gatewayDefineService.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/refreshRoute")
    public boolean refreshGateway(){
        try {
            this.gatewayDefineService.loadRouteDefinition();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
