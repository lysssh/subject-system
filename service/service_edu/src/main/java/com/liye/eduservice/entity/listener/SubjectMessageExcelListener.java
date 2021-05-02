package com.liye.eduservice.entity.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.eduservice.entity.SubjectMessage;
import com.liye.eduservice.entity.SubjectRelation;
import com.liye.eduservice.entity.excel.SubjectData;
import com.liye.eduservice.service.SubjectMessageService;
import com.liye.eduservice.service.SubjectRelationService;
import com.liye.servicebase.exceptionhandler.SubjectSystemException;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice.entity.listener
 * @create_time 2021/3/12 15:57
 */
public class SubjectMessageExcelListener extends AnalysisEventListener<SubjectData> {

    public SubjectRelationService subjectRelationService;
    public SubjectMessageService subjectMessageService;

    public SubjectMessageExcelListener(){}

    public SubjectMessageExcelListener(SubjectRelationService subjectRelationService,SubjectMessageService subjectMessageService) {
        this.subjectRelationService=subjectRelationService;
        this.subjectMessageService=subjectMessageService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {

        if(subjectData==null) {
            throw new SubjectSystemException(20001,"数据为空");
        }
        SubjectRelation subjectRelation = this.existSubject(subjectRelationService, subjectData.getOneSubjectName(), subjectData.getTwoSubjectName());
        if(subjectRelation==null) {
            subjectRelation.setSubjectTitle(subjectData.getOneSubjectName());
            if(subjectData.getTwoSubjectName().equals("start")) {
                subjectRelation.setParentId(subjectData.getTwoSubjectName());
            }else {
                QueryWrapper<SubjectMessage> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("subject_title",subjectData.getTwoSubjectName());
                SubjectMessage one = subjectMessageService.getOne(queryWrapper);
                subjectRelation.setParentId(one.getId());
            }
            subjectRelationService.save(subjectRelation);
        }


    }



    //判断是否重复添加
    private SubjectRelation existSubject(SubjectRelationService subjectService, String oneName, String twoName) {
        QueryWrapper<SubjectRelation> wrapper=new QueryWrapper<>();
        wrapper.eq("subject_title",oneName);
        if(twoName.equals("start")) {
            wrapper.eq("parent_id",twoName);
        }else {
            QueryWrapper<SubjectMessage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("subject_title",twoName);
            SubjectMessage one = subjectMessageService.getOne(queryWrapper);
            wrapper.eq("parent_id",one.getId());
        }
        SubjectRelation Subject=subjectService.getOne(wrapper);
        return Subject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
