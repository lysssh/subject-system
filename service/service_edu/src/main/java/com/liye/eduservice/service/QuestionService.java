package com.liye.eduservice.service;

import com.liye.eduservice.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liye.eduservice.entity.question.ProfessionalVo;

import java.util.List;

/**
 * <p>
 * 题库 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-19
 */
public interface QuestionService extends IService<Question> {
    List<ProfessionalVo> getAll();
}
