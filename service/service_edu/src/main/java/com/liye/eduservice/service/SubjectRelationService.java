package com.liye.eduservice.service;

import com.liye.eduservice.entity.SubjectRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 科目关系表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
public interface SubjectRelationService extends IService<SubjectRelation> {

    void saveSubject(MultipartFile file, SubjectRelationService subjectRelationService, SubjectMessageService subjectMessageService);
}
