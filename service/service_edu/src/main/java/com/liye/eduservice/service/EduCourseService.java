package com.liye.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liye.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liye.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
public interface EduCourseService extends IService<EduCourse> {
    CoursePublishVo getPublishCourseInfo(String courseId);

    boolean removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, EduCourse courseFrontVo);
}
