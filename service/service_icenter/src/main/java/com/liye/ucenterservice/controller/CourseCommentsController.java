package com.liye.ucenterservice.controller;


import com.liye.commonutils.R;
import com.liye.ucenterservice.entity.CourseComments;
import com.liye.ucenterservice.entity.vo.CommentsVo;
import com.liye.ucenterservice.service.CourseCommentsService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讨论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/ucenterservice/course-comments")
public class CourseCommentsController {


    @Autowired
    private CourseCommentsService courseCommentsService;

    @ApiModelProperty("添加评论")
    @PostMapping("addComments")
    public R addComments(@RequestBody CourseComments courseComments) {
        boolean save = courseCommentsService.save(courseComments);
        if(save) {
            return R.ok();
        }
        return R.error();
    }

    @ApiModelProperty("修改评论")
    @PostMapping("updataComments")
    public R updataComments(@RequestBody CourseComments courseComments) {
        boolean save = courseCommentsService.updateById(courseComments);
        if(save) {
            return R.ok();
        }
        return R.error();
    }

    @ApiModelProperty("删除评论")
    @DeleteMapping("{id}")
    public R deleteComments(@PathVariable String id) {
        boolean save = courseCommentsService.removeById(id);
        if(save) {
            return R.ok();
        }
        return R.error();
    }


    @ApiModelProperty("查询全部评论")
    @DeleteMapping("findAll/{id}")
    public R findAll(@PathVariable String id) {
        List<CommentsVo> all = courseCommentsService.findAll(id);
        return R.ok().data("list",all);
    }


}

