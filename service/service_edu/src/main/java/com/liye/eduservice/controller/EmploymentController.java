package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.Employment;
import com.liye.eduservice.service.EmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 就业去向 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-06
 */
@RestController
@RequestMapping("/eduservice/employment")
public class EmploymentController {

    @Autowired
    private EmploymentService employmentService;

    @GetMapping("findAll/{pathId}")
    public R findAll(@PathVariable String pathId) {
        QueryWrapper<Employment> wrapper = new QueryWrapper<>();
        wrapper.eq("path_id",pathId);
        wrapper.orderByDesc("year");
        List<Employment> list = employmentService.list(wrapper);
        return R.ok().data("list",list);
    }

    @GetMapping("findById/{id}")
    public R findById(@PathVariable String id) {
        Employment byId = employmentService.getById(id);
        return R.ok().data("object",byId);
    }

    @PostMapping("add")
    public R addEmployment(@RequestBody Employment employment) {
        boolean save = employmentService.save(employment);
        if(save) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("updata")
    public R updataEmployment(@RequestBody Employment employment) {
        boolean b = employmentService.updateById(employment);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    @DeleteMapping("{id}")
    public R delete(@PathVariable String id) {
        boolean b = employmentService.removeById(id);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

}

