package com.liye.eduservice.service;

import com.liye.eduservice.entity.PathMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liye.eduservice.entity.vo.PathMessageVo;

import java.util.List;

/**
 * <p>
 * 学习路线具体内容 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-02
 */
public interface PathMessageService extends IService<PathMessage> {

    List<PathMessageVo> getPathMessage(String pathId);

}
