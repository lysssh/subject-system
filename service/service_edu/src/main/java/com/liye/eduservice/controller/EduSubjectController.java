package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.EduSubject;
import com.liye.eduservice.entity.EduTeacher;
import com.liye.eduservice.entity.subject.OneSubject;
import com.liye.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    /**
     * 1.获取上传文件,读取内容
     * */

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {

        //上传过来的数据
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();

    }

    //添加课程分类
    @PostMapping("insertSubject")
    public R insertSubject(@RequestBody EduSubject subject) {
        boolean save = eduSubjectService.save(subject);
        if(save) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("findById/{id}")
    public R findById(@PathVariable String id) {
        EduSubject byId = eduSubjectService.getById(id);
        return R.ok().data("subject",byId);
    }

    //根据专业ID获取科目
    @GetMapping("getByProfessionalId/{professionalId}")
    public R getByProfessionalI(@PathVariable String professionalId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("professional_id",professionalId);
        List<EduSubject> list = eduSubjectService.list(wrapper);
        return R.ok().data("list",list);
    }

    //获取全部课目
    @GetMapping("findAll/{current}/{limit}")
    public R findAll(@PathVariable Long current,@PathVariable Long limit) {
        R r = eduSubjectService.gettAllSubject(current, limit);
        return r;
    }
    //修改科目
    @PostMapping("update")
    public R updata(@RequestBody EduSubject subject) {
        boolean b = eduSubjectService.updateById(subject);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    @DeleteMapping("{id}")
    public R delete(@PathVariable String id) {
        boolean b = eduSubjectService.removeById(id);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("pageSubject/{current}/{limit}")
    public R pageSubject(@PathVariable Long current,@PathVariable Long limit) {
        Page<EduSubject> page=new Page<>(current,limit);
        eduSubjectService.page(page,null);
        long total=page.getTotal();//总记录数
        List<EduSubject> list=page.getRecords();
        return R.ok().data("total",total).data("items",list);
    }

    //获取全部科目
    @GetMapping("findAllSubject")
    public R findAll() {
        List<EduSubject> list = eduSubjectService.list(null);
        return R.ok().data("list",list);
    }

    //课程分类的列表功能,树形显示
    @PostMapping ("getAllSubject")
    public R getAllSubject() {

        List<OneSubject> list=eduSubjectService.getAllOneTwoSubject();

        return R.ok().data("list",list);

    }

}

