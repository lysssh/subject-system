package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.EduSubject;
import com.liye.eduservice.entity.PathMessage;
import com.liye.eduservice.entity.vo.PathMessageVo;
import com.liye.eduservice.service.PathMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学习路线具体内容 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/eduservice/path-message")
public class PathMessageController {

    @Autowired
    private PathMessageService pathMessageService;


    @GetMapping("findPath/{id}")
    public R findPath(@PathVariable String id) {
        QueryWrapper<PathMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("path_id",id);
        List<PathMessage> list = pathMessageService.list(wrapper);
        return R.ok().data("list",list);
    }

    //通过pathId获取路线完整信息
    @GetMapping("getAllMessageByPathId/{pathId}")
    public R getAllMessageByPathId(@PathVariable String pathId) {
        List<PathMessageVo> pathMessage = pathMessageService.getPathMessage(pathId);
        return R.ok().data("list",pathMessage);
    }

    @PostMapping("add")
    public R add(@RequestBody PathMessage pathMessage) {
        boolean save = pathMessageService.save(pathMessage);
        if(save) {
            return R.ok();
        }
        return R.error();
    }

    @DeleteMapping("{id}")
    public R delete(@PathVariable String id) {
        boolean b = pathMessageService.removeById(id);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("update")
    public R update(@RequestBody PathMessage pathMessage) {
        boolean b = pathMessageService.updateById(pathMessage);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    //通过Id查询信息
    @GetMapping("findById/{id}")
    public R findById(@PathVariable String id) {
        PathMessage byId = pathMessageService.getById(id);
        return R.ok().data("data",byId);
    }

}

