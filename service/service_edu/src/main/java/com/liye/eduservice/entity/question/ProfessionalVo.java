package com.liye.eduservice.entity.question;

import lombok.Data;

import java.util.List;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice.entity.question
 * @create_time 2021/4/19 20:44
 */
@Data
public class ProfessionalVo {
    private String id;
    private String title;
    private List<subjectVo> list;
}
