package cn.springcloud.book.feign;

import cn.springcloud.book.interceptor.FeignConfiguration;
import cn.springcloud.book.interceptor.FeignInterveptor;
import cn.springcloud.book.model.TUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(value = "MICROSERVICE-PROVIDER-USER",configuration = FeignConfiguration.class)
public interface UserFeign {
    @RequestMapping(value = "/findAllUsers",method = RequestMethod.POST)
    List<TUser> findAllUsers();

}
