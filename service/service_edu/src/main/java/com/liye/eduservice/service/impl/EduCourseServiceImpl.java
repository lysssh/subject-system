package com.liye.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liye.eduservice.entity.EduChapter;
import com.liye.eduservice.entity.EduCourse;
import com.liye.eduservice.entity.vo.CoursePublishVo;
import com.liye.eduservice.mapper.EduCourseMapper;
import com.liye.eduservice.service.EduChapterService;
import com.liye.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liye.eduservice.service.EduVideoService;
import com.liye.servicebase.exceptionhandler.SubjectSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

   @Autowired
   private EduVideoService videoService;

   @Autowired
   private EduChapterService ChapterService;


    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    @Override
    public boolean removeCourse(String courseId) {

        boolean video=videoService.removeByCourseId(courseId);
        if(!video) {
            throw new SubjectSystemException(20001,"小节删除失败");
        }

        boolean chapter=ChapterService.removeChapterByCourseId(courseId);
        if(!chapter) {
            throw new SubjectSystemException(20001,"章节删除失败");
        }

        int i = baseMapper.deleteById(courseId);
        return i>0;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, EduCourse courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
//        //判断条件值是否为空，不为空拼接
//        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
//            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
//        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCount())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreate())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

//        if (!StringUtils.isEmpty(courseFrontVo.getPrice())) {//价格
//            wrapper.orderByDesc("price");
//        }

        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }
}
