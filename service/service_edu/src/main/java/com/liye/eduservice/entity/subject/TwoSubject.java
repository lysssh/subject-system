package com.liye.eduservice.entity.subject;

import com.liye.eduservice.entity.question.QuestionVo;
import lombok.Data;

import java.util.List;

/**
 * @author liye
 * @create 2021-01-19-12:40
 */
@Data
public class TwoSubject {

    private String id;


    private String title;

    private List<QuestionVo> list;
}
