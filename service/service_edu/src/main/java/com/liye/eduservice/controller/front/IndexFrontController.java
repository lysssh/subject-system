package com.liye.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.EduCourse;
import com.liye.eduservice.entity.EduTeacher;
import com.liye.eduservice.service.EduCourseService;
import com.liye.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @Cacheable(value = "course",key="'selectCourse'")
    @GetMapping("indexCourse")
    public R index() {
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("buy_count");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);
        return R.ok().data("courseList",eduList);
    }

    @Cacheable(value = "teacher",key="'selectTeacher'")
    @GetMapping("indexTeacher")
    public R indexTeacher() {
        //查询前8条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 8");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);
        return R.ok().data("teacherList",teacherList);
    }
}
