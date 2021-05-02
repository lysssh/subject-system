package com.liye.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.eduservice.entity.EduCourse;
import com.liye.eduservice.entity.PathMessage;
import com.liye.eduservice.entity.vo.PathMessageVo;
import com.liye.eduservice.mapper.PathMessageMapper;
import com.liye.eduservice.service.EduCourseService;
import com.liye.eduservice.service.PathMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 学习路线具体内容 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-02
 */
@Service
public class PathMessageServiceImpl extends ServiceImpl<PathMessageMapper, PathMessage> implements PathMessageService {

    @Resource
    private EduCourseService eduCourse;

    @Override
    public List<PathMessageVo> getPathMessage(String pathId) {
        QueryWrapper<PathMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("path_id",pathId);
        List<PathMessage> pathMessages = baseMapper.selectList(wrapper);
        List<PathMessageVo> list = new ArrayList<>();
        if(pathMessages.size()==0) {
            return list;
        }

        for(PathMessage pathMessage : pathMessages) {
            QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
            courseQueryWrapper.eq("subject_id",pathMessage.getSubjectId());
            List<EduCourse> list1 = eduCourse.list(courseQueryWrapper);
            PathMessageVo pathMessageVo = new PathMessageVo();
            pathMessageVo.setSubjectTitle(pathMessage.getSubjectId());
            pathMessageVo.setList(new ArrayList<>(list1));
            list.add(pathMessageVo);
        }
        return list;

    }
}
