package com.liye.eduservice.service;

import com.liye.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liye.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-12
 */
public interface EduChapterService extends IService<EduChapter> {


    List<ChapterVo> getChapterByCourseId(String courseId);

    boolean deleteChapterById(String chapterId);

    boolean removeChapterByCourseId(String courseId);
}
