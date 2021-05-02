package com.liye.eduservice.controller;



import com.liye.commonutils.R;
import com.liye.eduservice.entity.EduChapter;
import com.liye.eduservice.entity.chapter.ChapterVo;
import com.liye.eduservice.service.EduChapterService;
import com.liye.eduservice.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;



    @ApiOperation("根据课程ID查询课程目录")
    @GetMapping("getChapterByCourseId/{courseId}")
    public R getChapterByCourseId(@PathVariable String courseId) {
        List<ChapterVo> list= chapterService.getChapterByCourseId(courseId);
        return R.ok().data("chapter", list);
    }

    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        boolean save = chapterService.save(eduChapter);
        if(save) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        boolean b = chapterService.updateById(eduChapter);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("根据ID查询章节")
    @GetMapping("getById/{chapterId}")
    public R getById(@PathVariable String chapterId) {
        EduChapter byId = chapterService.getById(chapterId);
        return R.ok().data("chapter",byId);
    }

    @ApiOperation("删除章节")
    @DeleteMapping("{chapterId}")
    public R delete(@PathVariable String chapterId) {

        boolean b=chapterService.deleteChapterById(chapterId);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

}

