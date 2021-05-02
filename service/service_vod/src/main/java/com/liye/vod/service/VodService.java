package com.liye.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface VodService {
    //上传视频到阿里云
    String uploadVideoAly(MultipartFile file);

    boolean removeById(String id) throws ClientException;

    boolean removeAllCourseVideo(List<String> videoIdList);

    String uploadByUrl(String url) throws UnsupportedEncodingException, ClientException, Exception;

    String getPlayURLById(String id) throws Exception;
}
