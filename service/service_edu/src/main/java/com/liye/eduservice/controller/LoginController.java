package com.liye.eduservice.controller;

import com.liye.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice.controller
 * @create_time 2021/4/2 21:49
 */

@RestController
@RequestMapping("/eduservice/course")
public class LoginController {


    @PostMapping("login")
    public R login() {
        return R.ok().data("token","token");
    }


    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://edu-gulioarent.oss-cn-beijing.aliyuncs.com/%E6%95%99%E5%B8%88%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpeg");
    }
}
