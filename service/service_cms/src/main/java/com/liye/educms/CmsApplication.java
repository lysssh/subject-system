package com.liye.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.educms
 * @create_time 2021/3/15 11:17
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.liye"})
@MapperScan("com.liye.educms.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
