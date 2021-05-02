package com.liye.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.ucenterservice
 * @create_time 2021/3/15 18:12
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.liye"})
@MapperScan("com.liye.ucenterservice.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class,args);
    }
}
