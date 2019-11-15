package cn.springcloud.book.gateway.repository;

import cn.springcloud.book.gateway.model.GatewayDefine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayDefineRepository extends JpaRepository<GatewayDefine, String> {

}
