package com.liye.ucenterservice.service;

import com.liye.ucenterservice.entity.CourseComments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liye.ucenterservice.entity.vo.CommentsVo;

import java.util.List;

/**
 * <p>
 * 讨论 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-13
 */
public interface CourseCommentsService extends IService<CourseComments> {
    List<CommentsVo> findAll(String id);
}
