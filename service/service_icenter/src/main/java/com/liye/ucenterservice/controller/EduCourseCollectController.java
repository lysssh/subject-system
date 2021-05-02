package com.liye.ucenterservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.commonutils.R;
import com.liye.ucenterservice.client.courseClient;
import com.liye.ucenterservice.entity.EduCourseCollect;
import com.liye.ucenterservice.service.EduCourseCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.List;

/**
 * <p>
 * 课程收藏 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/ucenterservice/edu-course-collect")
public class EduCourseCollectController {

    @Autowired
    private EduCourseCollectService eduCourseCollectService;

    @Autowired
    private courseClient client;

//    添加收藏课程
    @PostMapping("addCouresCollect")
    public R addCouresCollect(@RequestBody EduCourseCollect eduCourseCollect) {
        boolean save = eduCourseCollectService.save(eduCourseCollect);
        if(save) {
            return R.ok().data("success","收藏成功").data("error",null);
        }
        return R.ok().data("error","收藏失败");
    }

    @GetMapping("findAll")
    public R findAll () {
        List<EduCourseCollect> list = eduCourseCollectService.list(null);
        return R.ok().data("list",list);
    }

//    根据ID查看课程详细信息
    @GetMapping("selectById/{courseId}")
    public R selectById(@PathVariable String courseId) {
        R id = client.findId(courseId);
        return id;
    }

    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id) {
        boolean b = eduCourseCollectService.removeById(id);
        if(b) {
            return R.ok().data("success","删除成功");
        }
        return R.error().data("error","删除失败");
    }


    //根据ID获取对应用户的课程
    @GetMapping("getCourseByUserId/{id}")
    public R getCourseByUserId(@PathVariable String id) {
        QueryWrapper<EduCourseCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",id);
        List<EduCourseCollect> list = eduCourseCollectService.list(wrapper);
        return R.ok().data("list",list);
    }

    //判断课程是否已经添加
    @GetMapping("judge/{userId}/{courseId}")
    public R judge(@PathVariable String userId,@PathVariable String courseId) {
        QueryWrapper<EduCourseCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",userId);
        wrapper.eq("course_id",courseId);

        int count = eduCourseCollectService.count(wrapper);
        if(count>0) {
            return R.ok().data("flag",false);
        }
        return R.ok().data("flag",true);
    }
}

