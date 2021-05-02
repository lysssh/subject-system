package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.EduCourse;
import com.liye.eduservice.entity.EduTeacher;
import com.liye.eduservice.entity.vo.CoursePublishVo;
import com.liye.eduservice.entity.vo.CourseQuery;
import com.liye.eduservice.service.EduCourseService;
import com.liye.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {


    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService teacherService;



    @ApiOperation("查询全部课程信息")
    @GetMapping("findAll")
    public R findAll() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }

    @ApiOperation("添加课程")
    @PostMapping("addCourse")
    public R addCourse(@RequestBody EduCourse eduCourse) {
        boolean save = eduCourseService.save(eduCourse);
        if(save) {
            return R.ok().data("courseId",eduCourse.getId());
        }
        return R.error();
    }


    @ApiOperation("修改课程")
    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody EduCourse eduCourse) {
        boolean b = eduCourseService.updateById(eduCourse);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("分页条件查询")
    @PostMapping("findPage/{current}/{limit}")
    public R findPage(@PathVariable long current,
                      @PathVariable long limit,
                      @RequestBody CourseQuery courseQuery) {
        Page<EduCourse> page = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String name = courseQuery.getTitle();
        String teacherId=courseQuery.getTeacherId();
        String professionalId=courseQuery.getProfessionalId();
        String subjectId = courseQuery.getSubjectId();
        String status=courseQuery.getStatus();

        if(!StringUtils.isEmpty(name)) {
            wrapper.like("title",name);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.like("teacher_id",teacherId);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }

        if(!StringUtils.isEmpty(professionalId)) {
            wrapper.eq("professional_id",professionalId);
        }

        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        eduCourseService.page(page,wrapper);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.ok().data("total",total).data("list",records);
    }

    @ApiOperation("根据ID查询")
    @GetMapping("findId/{id}")
    public R findId(@PathVariable String id) {
        EduCourse byId = eduCourseService.getById(id);
        EduTeacher byId1 = teacherService.getById(byId.getTeacherId());
        byId.setTeacherName(byId1.getName());
        return R.ok().data("course",byId);
    }

    @ApiOperation("根据课程ID做发布确认")
    @GetMapping("publishCourseInfo/{courseId}")
    public R publishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo publishCourseInfo = eduCourseService.getPublishCourseInfo(courseId);
        return R.ok().data("courseInfo",publishCourseInfo);
    }


    @ApiOperation("课程最终发布")
    @GetMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse= new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        boolean b = eduCourseService.updateById(eduCourse);
        if(b) {
            return R.ok();
        }
        return R.error();
    }


    @ApiOperation("删除课程")
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        boolean b=eduCourseService.removeCourse(courseId);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    //通过科目Id查询对应课程
    @GetMapping("findAllBySubjectId/{subjectId}")
    public R findAllBySubjectId(@PathVariable String subjectId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("subject_id",subjectId);
        List<EduCourse> list = eduCourseService.list(wrapper);
        return R.ok().data("list",list);
    }

    //通过讲师Id查询课程信息
    @GetMapping("getCourseByTeacherId/{teacherId}")
    public R getCourseByTeacherId(@PathVariable String teacherId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        wrapper.select("id","cover");
        List<EduCourse> list = eduCourseService.list(wrapper);
        return R.ok().data("list",list);
    }

    @GetMapping("getAllCourseByCourseTitle/{title}")
    public R getAllCourseByCourseTitle(@PathVariable String title) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.like("title",title);
        List<EduCourse> list = eduCourseService.list(wrapper);
        for(int i=0;i<list.size();i++) {
            EduTeacher byId = teacherService.getById(list.get(i).getTeacherId());
            list.get(i).setTeacherName(byId.getName());
        }
        return R.ok().data("list",list);
    }
}

