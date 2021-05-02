package com.liye.eduservice.entity.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.liye.eduservice.entity.EduSubject;
import com.liye.eduservice.entity.excel.SubjectData;

import com.liye.eduservice.service.EduSubjectService;
import com.liye.servicebase.exceptionhandler.SubjectSystemException;


/**
 * @author liye
 * @create 2021-01-18-19:12
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {}

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null) {
            throw new SubjectSystemException(20001,"数据为空");
        }
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if(existOneSubject==null) {
            existOneSubject=new EduSubject();
//            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }


        EduSubject existTwoSubject=this.existTwoSubject(eduSubjectService,subjectData.getTwoSubjectName(),existOneSubject.getId());
        if(existTwoSubject==null) {
            existTwoSubject=new EduSubject();
//            existTwoSubject.setParentId(existOneSubject.getId());
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }

    }


    //判断一级分类不能重复添加

    private EduSubject existOneSubject(EduSubjectService subjectService,String name) {
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject oneSubject=subjectService.getOne(wrapper);
        return oneSubject;
    }


    //判断一级分类不能重复添加

    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject=subjectService.getOne(wrapper);
        return twoSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
