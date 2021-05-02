package com.liye.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.eduservice.entity.EduSubject;
import com.liye.eduservice.entity.SubjectMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 科目信息表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
public interface SubjectMessageService extends IService<SubjectMessage> {

    List<SubjectMessage> getByTitle(String title);


}
