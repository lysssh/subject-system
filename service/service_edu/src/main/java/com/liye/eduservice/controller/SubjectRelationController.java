package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.SubjectRelation;
import com.liye.eduservice.service.SubjectMessageService;
import com.liye.eduservice.service.SubjectRelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 科目关系表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/eduservice/subjectrelation")
public class SubjectRelationController {

    @Autowired
    private SubjectRelationService subjectRelationService;

    @Autowired
    private SubjectMessageService subjectMessageService;

    @ApiOperation("添加科目联系")
    @PostMapping("addSubjectRelation")
    public R addSubjectRelation(MultipartFile file) {
        subjectRelationService.saveSubject(file,subjectRelationService,subjectMessageService);
        return R.ok();
    }

    @ApiOperation("查询前置课程")
    @GetMapping("before/{title}")
    public R before(@PathVariable String title) {
        QueryWrapper<SubjectRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_title",title);
        SubjectRelation one = subjectRelationService.getOne(queryWrapper);
        return R.ok().data("items",one);
    }

    @ApiOperation("查询未来课程")
    @GetMapping("after/{id}")
    public R after(@PathVariable String id) {
        QueryWrapper<SubjectRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        SubjectRelation one = subjectRelationService.getOne(queryWrapper);
        return R.ok().data("items",one);
    }
}

