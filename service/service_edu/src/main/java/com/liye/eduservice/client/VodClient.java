package com.liye.eduservice.client;

import com.liye.commonutils.R;
import com.liye.eduservice.client.clientImpl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice.client
 * @create_time 2021/3/13 19:00
 */

@Component
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
public interface VodClient {

    //定义调用方法的路径
    @DeleteMapping("/eduvod/video/removeById/{id}")
    public R removeById(@PathVariable("id") String id);

    //删除多个视频
    @DeleteMapping("/eduvod/video/removeByCourseId")
    public R removeByCourseId(@RequestParam("videoIdList") List<String> videoIdList);

}
