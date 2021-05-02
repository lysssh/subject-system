package com.liye.eduservice.client;

import com.liye.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice.client
 * @create_time 2021/4/24 22:21
 */

@Component
@FeignClient(name = "service-oss")
public interface OssClient {

    @DeleteMapping("/eduoss/fileoss/{imageName}")
    public R deleteImage(@PathVariable("imageName") String imageName);

}
