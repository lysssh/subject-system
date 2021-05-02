package com.liye.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.EduSubject;
import com.liye.eduservice.entity.Professional;
import com.liye.eduservice.entity.excel.SubjectData;
import com.liye.eduservice.entity.listener.SubjectExcelListener;
import com.liye.eduservice.entity.subject.OneSubject;
import com.liye.eduservice.entity.subject.Subject;
import com.liye.eduservice.entity.subject.TwoSubject;
import com.liye.eduservice.mapper.EduSubjectMapper;
import com.liye.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liye.eduservice.service.ProfessionalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private ProfessionalService professionalService;


    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream in=file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<OneSubject> getAllOneTwoSubject() {

        QueryWrapper<EduSubject> oneWrapper=new QueryWrapper<>();

        //1.查询出所有的一级分类 parent_id=0
        oneWrapper.eq("parent_id","0");
        //baseMapper:继承类中封装好了
        List<EduSubject> list1=baseMapper.selectList(oneWrapper);

        //2.查询出所有的二级分类 parent_id!=0

        //创建List集合,封装数据
        List<OneSubject> list=new ArrayList<>();
        for(EduSubject onesubject : list1) {
            OneSubject oneSubject=new OneSubject();
//            oneSubject.setId(eduSubject1.getId());
//            oneSubject.setTitle(eduSubject1.getTitle());
            BeanUtils.copyProperties(onesubject,oneSubject);
            QueryWrapper<EduSubject> twoWrapper=new QueryWrapper<>();
            twoWrapper.eq("parent_id",onesubject.getId());
            List<EduSubject> twoList = baseMapper.selectList(twoWrapper);
            for(EduSubject twosubject : twoList) {
                TwoSubject twoSubject=new TwoSubject();
//                subject.setId(twoSubject.getId());
//                subject.setTitle(twoSubject.getTitle());
                BeanUtils.copyProperties(twosubject,twoSubject);
                oneSubject.getChildren().add(twoSubject);
            }
            list.add(oneSubject);
        }
        return list;
    }

    @Override
    public R gettAllSubject(Long current, Long limit) {
        List<Subject> list = new ArrayList<>();
        Page<Professional> page = new Page<>(current,limit);
        QueryWrapper<Professional> wrapper = new QueryWrapper<>();
        wrapper.select("id","title");
        IPage<Professional> page1 = professionalService.page(page, wrapper);
        List<Professional> professional = page1.getRecords();
        long total = baseMapper.selectCount(null);

        for(Professional e : professional) {
            QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
            wrapper.eq("professional_id",e.getId());
            List<EduSubject> eduSubjects = baseMapper.selectList(wrapper1);
            for(EduSubject eduSubject : eduSubjects) {
                Subject subject = new Subject();
                subject.setProfessionalTitle(e.getTitle());
                subject.setTitle(eduSubject.getTitle());
                subject.setId(eduSubject.getId());
                list.add(subject);
            }
        }
        return R.ok().data("items",list).data("total",total);
    }
}
