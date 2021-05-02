package com.liye.eduservice.controller;


import com.liye.commonutils.R;
import com.liye.eduservice.entity.SubjectMessage;
import com.liye.eduservice.service.SubjectMessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 科目信息表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/eduservice/subjectmessage")
public class SubjectMessageController {

    @Autowired
    private SubjectMessageService subjectMessageService;


    @ApiOperation(value = "查询全部科目")
    @GetMapping("findAll")
    public R findAll() {
        List<SubjectMessage> list = subjectMessageService.list(null);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "逻辑删除科目")
    @DeleteMapping("{id}")
    public R deleteById(@ApiParam(value = "课程ID") @PathVariable String id) {
        boolean b = subjectMessageService.removeById(id);
        if(b) {
            return R.ok();
        }
        return R.error().message("删除失败");
    }


    @ApiOperation(value = "通过标题查询科目")
    @GetMapping("findSubject/{title}")
    public R findSubjectBytitle(@ApiParam(value = "课程标题") @PathVariable String title) {
        List<SubjectMessage> subject=subjectMessageService.getByTitle(title);
        return R.ok().data("subject",subject);
    }



    @ApiOperation(value = "添加科目")
    @PostMapping("addSubject")
    public R addSubject(@RequestBody SubjectMessage subject) {
        boolean save = subjectMessageService.save(subject);
        if(save) {
            return R.ok().message("添加科目成功");
        }
        return R.error().message("添加科目失败");
    }
    @ApiOperation(value = "修改科目信息")
    @PostMapping("updateSubject")
    public R updateSubject(@RequestBody SubjectMessage subject) {
        boolean b = subjectMessageService.updateById(subject);
        if(b) {
            return R.ok();
        }
        return R.error();
    }
}

