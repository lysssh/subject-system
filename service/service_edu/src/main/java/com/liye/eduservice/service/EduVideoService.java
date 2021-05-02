package com.liye.eduservice.service;

import com.liye.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean removeByCourseId(String courseId);
}
