package com.liye.eduservice.entity.vo;

import com.liye.eduservice.entity.EduCourse;
import lombok.Data;

import java.util.List;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice.entity.vo
 * @create_time 2021/4/8 20:20
 */

@Data
public class PathMessageVo {

    private String subjectTitle;

    private List<EduCourse> list;
}
