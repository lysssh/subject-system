package com.liye.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.eduservice.client.VodClient;
import com.liye.eduservice.entity.EduChapter;
import com.liye.eduservice.entity.EduVideo;
import com.liye.eduservice.entity.chapter.ChapterVo;
import com.liye.eduservice.entity.chapter.VideoVo;
import com.liye.eduservice.mapper.EduChapterMapper;
import com.liye.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liye.eduservice.service.EduVideoService;
import com.liye.servicebase.exceptionhandler.SubjectSystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterByCourseId(String courseId) {

        //1.根据课程ID查章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> Chapters = baseMapper.selectList(wrapperChapter);

        //2.根据课程ID查小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> videos = videoService.list(wrapperVideo);

        //3.创建最终的List集合
        List<ChapterVo> finalList = new ArrayList<>();

        for(int i=0;i<Chapters.size();i++) {
            ChapterVo chapterVo =new ChapterVo();
            BeanUtils.copyProperties(Chapters.get(i),chapterVo);

            finalList.add(chapterVo);

            for(int j=0;j<videos.size();j++) {

                if(videos.get(j).getChapterId().equals(Chapters.get(i).getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(videos.get(j),videoVo);
                    chapterVo.getList().add(videoVo);
                }
            }

        }

        return finalList;
    }

    @Override
    public boolean deleteChapterById(String chapterId) {
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("chapter_id",chapterId);
        wrapperVideo.select("video_source_id");
        wrapperVideo.select("id");
        List<EduVideo> eduVideos = videoService.list(wrapperVideo);

        List<String> list = new ArrayList<>();
        for(int i=0;i<eduVideos.size();i++) {
            eduVideoService.removeById(eduVideos.get(i).getId());
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        //查询到几条记
        int count = videoService.count(wrapper);
        if(count>0) {
            throw new SubjectSystemException(20001,"请先删除所有小节");
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }

    }

    @Override
    public boolean removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
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
