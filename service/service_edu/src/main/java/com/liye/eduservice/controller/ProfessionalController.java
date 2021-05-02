package com.liye.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liye.commonutils.R;
import com.liye.eduservice.entity.EduTeacher;
import com.liye.eduservice.entity.Professional;
import com.liye.eduservice.service.ProfessionalService;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 专业 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/eduservice/professional")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    //根据id查询
    @GetMapping("findById/{id}")
    public R findById(@PathVariable String id) {
        Professional byId = professionalService.getById(id);
        return R.ok().data("professional",byId);
    }

    //查询全部专业
    @GetMapping("findAll")
    public R findAll() {
        List<Professional> list = professionalService.list(null);
        return R.ok().data("list",list);
    }

    //添加专业
    @PostMapping("addProfessional")
    public R addProfessional(@RequestBody Professional professional) {
        boolean save = professionalService.save(professional);
        if(save) {
            return R.ok();
        }
        return R.error();
    }

    @Delete("{id}")
    public R delete(@PathVariable String id) {
        boolean b = professionalService.removeById(id);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("updata")
    public R updata(@RequestBody Professional professional) {
        boolean b = professionalService.updateById(professional);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    //分页查询
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageProfessional(@ApiParam(name = "current",value = "数据页",required = true)@PathVariable long current,
                         @ApiParam(name = "limit",value = "数据量",required = true)@PathVariable long limit) {
        Page<Professional> page=new Page<>(current,limit);
        professionalService.page(page,null);
        long total=page.getTotal();//总记录数
        List<Professional> list=page.getRecords();
        return R.ok().data("total",total).data("items",list);
    }
}

