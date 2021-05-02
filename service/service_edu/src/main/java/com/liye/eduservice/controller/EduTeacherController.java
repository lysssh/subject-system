package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.EduTeacher;
import com.liye.eduservice.entity.vo.TeacherQuery;
import com.liye.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //1.查询全部讲师数据
    @ApiOperation(value = "查询全部讲师")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }


    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value ="教师ID" ,required = true)
                               @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if(flag) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@ApiParam(name = "current",value = "数据页",required = true)@PathVariable long current,
                         @ApiParam(name = "limit",value = "数据量",required = true)@PathVariable long limit) {
        Page<EduTeacher> page=new Page<>(current,limit);
        teacherService.page(page,null);
        long total=page.getTotal();//总记录数
        List<EduTeacher> list=page.getRecords();
        return R.ok().data("total",total).data("items",list);
    }

    @ApiOperation(value ="分页条件查询")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current",value = "数据页",required = true)@PathVariable long current,
                                  @ApiParam(name = "limit",value = "数据量",required = true)@PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {


        Page<EduTeacher> page=new Page<>(current,limit);
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();

        String name=teacherQuery.getName();
        Integer level=teacherQuery.getLevel();
        String begin=teacherQuery.getBegin();
        String end=teacherQuery.getEnd();


        if(!StringUtils.isEmpty(name)) {
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            queryWrapper.gt("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_modified",end);
        }

        queryWrapper.orderByDesc("gmt_modified");
        teacherService.page(page,queryWrapper);
        long total=page.getTotal();
        List<EduTeacher> list=page.getRecords();
        return R.ok().data("total",total).data("items",list);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        if(StringUtils.isEmpty(eduTeacher.getAvatar())) {
            eduTeacher.setAvatar("https://edu-gulioarent.oss-cn-beijing.aliyuncs.com/%E6%95%99%E5%B8%88%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpeg");
        }
        boolean save = teacherService.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    @ApiOperation(value = "根据Id查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)
                        @PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("items",eduTeacher);
    }


    @ApiOperation(value = "修改讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = teacherService.updateById(eduTeacher);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //通过讲师名称查询讲师
    @GetMapping("getAllTeacherByTeacherName/{name}")
    public R getAllTeacherByTeacherName(@PathVariable String name) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty("name")) {
            wrapper.like("name",name);
        }
        List<EduTeacher> list = teacherService.list(wrapper);
        return R.ok().data("list",list);
    }
}

