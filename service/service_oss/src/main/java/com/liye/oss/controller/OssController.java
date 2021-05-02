package com.liye.oss.controller;

import com.liye.commonutils.R;
import com.liye.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * @author liye
 * @create 2021-01-18-13:24
 */

@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {


    @Autowired
    private OssService ossService;
    //上传头像
    /**
     * MultipartFile:获取上传文件
     *
     */
    @PostMapping
    public R uploadOssFile(MultipartFile file) throws FileNotFoundException {

        String url=ossService.uploadFileAvatar(file);

        return R.ok().data("url",url);
    }

    @DeleteMapping("{imageName}")
    public R deleteImage(@PathVariable("imageName") String imageName) {
        boolean b = ossService.deleteFile(imageName);
        return R.ok();
    }

}
