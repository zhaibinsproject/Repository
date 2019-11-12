package com.itmuch.cloud.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"cn.springcloud.book.feign"})
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceMove {
  public static void main(String[] args) {
    SpringApplication.run(ServiceMove.class, args);
  }
}
