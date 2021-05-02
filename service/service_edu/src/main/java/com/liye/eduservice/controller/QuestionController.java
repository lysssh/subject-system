package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.Question;
import com.liye.eduservice.entity.question.ProfessionalVo;
import com.liye.eduservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 题库 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-19
 */
@RestController
@RequestMapping("/eduservice/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("getAllByProfessionalIdAndSubjectId/{professionalId}/{subjectId}")
    public R getAllByProfessionalIdAndSubjectId(@PathVariable("professionalId") String professionalId,
                                                @PathVariable("subjectId") String subjectId) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("professional_id",professionalId);
        wrapper.eq("subject_id",subjectId);
        List<Question> list = questionService.list(wrapper);
        return R.ok().data("list",list);
    }

    @PostMapping("addQuestion")
    public R addQuestion(@RequestBody Question question) {
        boolean save = questionService.save(question);
        return R.ok();
    }

    @GetMapping("findAll")
    public R findAll() {
        List<ProfessionalVo> all = questionService.getAll();
        return R.ok().data("list",all);
    }

}

