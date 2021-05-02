package com.liye.ucenterservice.client;

import com.liye.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.ucenterservice.client
 * @create_time 2021/3/27 9:27
 */
@Component
@FeignClient(name = "service-edu")
public interface courseClient {

    @GetMapping("/eduservice/course/findId/{id}")
    public R findId(@PathVariable("id") String id);
}
