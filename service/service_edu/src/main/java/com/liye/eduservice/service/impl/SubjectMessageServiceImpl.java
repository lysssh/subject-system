package com.liye.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liye.eduservice.entity.EduTeacher;
import com.liye.eduservice.entity.SubjectMessage;
import com.liye.eduservice.mapper.SubjectMessageMapper;
import com.liye.eduservice.service.SubjectMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 科目信息表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@Service
public class SubjectMessageServiceImpl extends ServiceImpl<SubjectMessageMapper, SubjectMessage> implements SubjectMessageService {

    @Override
    public List<SubjectMessage> getByTitle(String title) {
        Page<SubjectMessage> page=new Page<>(1,10);
        QueryWrapper<SubjectMessage> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            queryWrapper.like("title",title);
        }
        IPage<SubjectMessage> subjectMessageIPage = baseMapper.selectPage(page, queryWrapper);
        return subjectMessageIPage.getRecords();
    }
}
