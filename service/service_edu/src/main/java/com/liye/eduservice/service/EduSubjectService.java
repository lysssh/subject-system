package com.liye.eduservice.service;

import com.liye.commonutils.R;
import com.liye.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liye.eduservice.entity.subject.OneSubject;
import com.liye.eduservice.entity.subject.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<OneSubject> getAllOneTwoSubject();



    R gettAllSubject(Long current, Long limit);
}
