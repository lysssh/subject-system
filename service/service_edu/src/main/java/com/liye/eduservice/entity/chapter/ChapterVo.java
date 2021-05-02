package com.liye.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice.entity.chapter
 * @create_time 2021/3/12 20:18
 */
@Data
public class ChapterVo {
    private String id;

    private String title;

    private List<VideoVo> list = new ArrayList<>();
}
