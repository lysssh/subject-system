package com.liye.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.eduservice.entity.EduSubject;
import com.liye.eduservice.entity.Professional;
import com.liye.eduservice.entity.Question;
import com.liye.eduservice.entity.question.ProfessionalVo;
import com.liye.eduservice.entity.question.subjectVo;
import com.liye.eduservice.mapper.QuestionMapper;
import com.liye.eduservice.service.EduSubjectService;
import com.liye.eduservice.service.ProfessionalService;
import com.liye.eduservice.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 题库 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-19
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {


    @Autowired
    private ProfessionalService professionalService;
    @Autowired
    private EduSubjectService subjectService;
    @Override
    public List<ProfessionalVo> getAll() {
        List<ProfessionalVo> finalList = new ArrayList<>();
        List<Professional> list = professionalService.list(null);
        for(Professional professional : list) {
            ProfessionalVo professionalVo = new ProfessionalVo();
            BeanUtils.copyProperties(professional,professionalVo);
            finalList.add(professionalVo);
        }
        for(ProfessionalVo professionalVo: finalList) {
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            wrapper.eq("professional_id",professionalVo.getId());
            List<EduSubject> list1 = subjectService.list(wrapper);
            List<subjectVo> subjectVoList = new ArrayList<>();
            for(EduSubject subject : list1) {
                subjectVo subjectVo = new subjectVo();
                BeanUtils.copyProperties(subject,subjectVo);
                QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
                questionQueryWrapper.eq("professional_id",professionalVo.getId());
                questionQueryWrapper.eq("subject_id",subject.getId());
                List<Question> questions = baseMapper.selectList(questionQueryWrapper);
                subjectVo.setList(questions);
                subjectVoList.add(subjectVo);
            }
            professionalVo.setList(subjectVoList);
        }
        return finalList;
    }
}
