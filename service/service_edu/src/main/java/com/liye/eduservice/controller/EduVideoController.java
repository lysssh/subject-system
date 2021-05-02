package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.commonutils.R;
import com.liye.eduservice.client.VodClient;
import com.liye.eduservice.entity.EduVideo;
import com.liye.eduservice.service.EduVideoService;
import com.liye.servicebase.exceptionhandler.SubjectSystemException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.peer.SystemTrayPeer;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {


    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        boolean save = videoService.save(eduVideo);
        if(!save) {
            return R.error();
        }
        return R.ok();
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        boolean b = videoService.updateById(eduVideo);
        if(b) {
            return R.ok();
        }
        return R.error();
    }
    @ApiOperation("删除小节")
    @DeleteMapping("{videoId}")
    public R deleteById(@PathVariable String videoId) {
        EduVideo byId = videoService.getById(videoId);
        if(!StringUtils.isEmpty(byId.getVideoSourceId())) {
            R r = vodClient.removeById(byId.getVideoSourceId());
            if(r.getCode()==20001) {
                throw new SubjectSystemException(20001,"删除视频失败");
            }
        }
        boolean b = videoService.removeById(videoId);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    //通过Id获取详细信息
    @GetMapping("getByIdVideo/{id}")
    public R getByIdVideo(@PathVariable String id) {
        EduVideo byId = videoService.getById(id);
        return R.ok().data("video",byId);
    }
}

