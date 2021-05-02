package com.liye.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.liye.eduservice.entity.SubjectRelation;
import com.liye.eduservice.entity.excel.SubjectData;
import com.liye.eduservice.entity.listener.SubjectExcelListener;
import com.liye.eduservice.entity.listener.SubjectMessageExcelListener;
import com.liye.eduservice.mapper.SubjectRelationMapper;
import com.liye.eduservice.service.SubjectMessageService;
import com.liye.eduservice.service.SubjectRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 科目关系表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@Service
public class SubjectRelationServiceImpl extends ServiceImpl<SubjectRelationMapper, SubjectRelation> implements SubjectRelationService {

    @Override
    public void saveSubject(MultipartFile file, SubjectRelationService subjectRelationService, SubjectMessageService subjectMessageService) {
        try {
            InputStream in=file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectMessageExcelListener(subjectRelationService,subjectMessageService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
