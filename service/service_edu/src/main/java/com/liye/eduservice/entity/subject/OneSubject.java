package com.liye.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liye
 * @create 2021-01-19-12:40
 */

@Data
public class OneSubject {


    private String id;


    private String title;

    //一个一级分类含有多个二级分类
    private  List<TwoSubject> children=new ArrayList<>();
}
