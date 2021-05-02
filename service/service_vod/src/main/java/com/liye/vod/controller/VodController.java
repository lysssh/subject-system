package com.liye.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.liye.commonutils.R;
import com.liye.servicebase.exceptionhandler.SubjectSystemException;
import com.liye.vod.Utils.ConstantVodUtils;
import com.liye.vod.Utils.InitObject;
import com.liye.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

    @PostMapping("uploadByUrl/{url}")
    public R uploadByUrl(@PathVariable String url) throws Exception {
        String videoId = vodService.uploadByUrl(url);
        return R.ok().data("videoId",videoId);
    }


    //通过视频ID删视频
    @DeleteMapping("removeById/{id}")
    public R removeById(@PathVariable String id) throws ClientException {
        boolean b = vodService.removeById(id);
        if(b) {
            return R.ok();
        }else {
            throw new SubjectSystemException(20001,"vod:视频删除失败");
        }
    }

    //删除多个视频
    @DeleteMapping("removeByCourseId")
    public R removeByCourseId(@RequestParam("videoIdList")List<String> videoIdList) {
        boolean b = vodService.removeAllCourseVideo(videoIdList);
        if(b) {
            return R.ok();
        }else {
            throw new SubjectSystemException(20001,"视频删除失败");
        }
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new SubjectSystemException(20001,"获取凭证失败");
        }
    }

    //通过Id获取视频地址
    @GetMapping("getPlayVideoById/{id}")
    public R getPlayVideoById(@PathVariable String id) throws Exception {
        String playURLById = vodService.getPlayURLById(id);
        return R.ok().data("url",playURLById);
    }
}
