package com.liye.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice.entity.vo
 * @create_time 2021/3/12 19:42
 */
@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程状态")
    private String status;


    @ApiModelProperty(value = "专业标题")
    private String professionalId;

    @ApiModelProperty(value = "课目标题")
    private String subjectId;

}
