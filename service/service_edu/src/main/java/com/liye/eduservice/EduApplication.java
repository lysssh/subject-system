package com.liye.eduservice;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice
 * @create_time 2021/3/11 10:02
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.liye"})
@MapperScan("com.liye.eduservice.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
