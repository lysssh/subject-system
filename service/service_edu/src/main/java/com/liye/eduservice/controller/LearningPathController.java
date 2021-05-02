package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.LearningPath;
import com.liye.eduservice.entity.PathMessage;
import com.liye.eduservice.entity.Professional;
import com.liye.eduservice.service.LearningPathService;
import com.liye.eduservice.service.PathMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 学习路线 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/eduservice/learning-path")
public class LearningPathController {

    @Autowired
    private LearningPathService learningPathService;

    @Autowired
    private PathMessageService pathMessageService;

    //根据id查询
    @GetMapping("findById/{id}")
    public R findById(@PathVariable String id) {
        LearningPath byId = learningPathService.getById(id);
        return R.ok().data("learningPath",byId);
    }

    @GetMapping("findAll")
    public R findAll() {
        List<LearningPath> list = learningPathService.list(null);
        return R.ok().data("list",list);
    }

    @PostMapping("addLearnPath")
    public R addLearnPath(@RequestBody LearningPath learningPath) {
        boolean save = learningPathService.save(learningPath);
        if(save) {
            return R.ok().data("learnPath",learningPath);
        }
        return R.error();
    }

    @PostMapping("update")
    public R updata(@RequestBody LearningPath learningPath) {
        boolean b = learningPathService.updateById(learningPath);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    @DeleteMapping("{id}")
    public R delete(@PathVariable String id) {
        QueryWrapper<PathMessage> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("path_id",id);

        List<PathMessage> list = pathMessageService.list(wrapper);
        List<String> ids = new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            ids.add(list.get(i).getId());
        }

        boolean b1 = pathMessageService.removeByIds(ids);
        if(!b1) {
            return R.error().data("error","路线信息删除失败");
        }
        boolean b = learningPathService.removeById(id);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    //分页查询
    @GetMapping("pageLearnPath/{current}/{limit}")
    public R pageLearnPath(@PathVariable Long current,@PathVariable Long limit) {
        Page<LearningPath> pathPage = new Page<>(current,limit);
        learningPathService.page(pathPage, null);
        List<LearningPath> records = pathPage.getRecords();
        long total =pathPage.getTotal();

        return R.ok().data("items",records).data("total",total);
    }
}

