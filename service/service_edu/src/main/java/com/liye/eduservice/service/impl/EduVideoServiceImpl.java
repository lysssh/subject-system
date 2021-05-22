package com.liye.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.commonutils.R;
import com.liye.eduservice.client.VodClient;
import com.liye.eduservice.entity.EduVideo;
import com.liye.eduservice.mapper.EduVideoMapper;
import com.liye.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.scene.effect.Light;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    @Autowired
    private VodClient vodClient;

    @Override
    public boolean removeByCourseId(String courseId) {

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);

        List<String> list = new ArrayList<>();
        for(int i=0;i<eduVideos.size();i++) {
            if(!StringUtils.isEmpty(eduVideos.get(i).getVideoSourceId())) {
                list.add(eduVideos.get(i).getVideoSourceId());
            }
        }
        
        if(list.size()!=0) {
            vodClient.removeByCourseId(list);
        }


        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        Integer integer = baseMapper.selectCount(wrapper);
        if(integer==0) {
            return true;
        }
        int delete = baseMapper.delete(wrapper);
        if(delete>0) {
            return true;
        }
        return false;
    }
}
