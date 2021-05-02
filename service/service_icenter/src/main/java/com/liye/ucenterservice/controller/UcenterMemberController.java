package com.liye.ucenterservice.controller;


import com.liye.commonutils.JwtUtils;
import com.liye.commonutils.R;
import com.liye.ucenterservice.entity.UcenterMember;
import com.liye.ucenterservice.entity.vo.RegisterVo;
import com.liye.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/ucenterservice/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember ucenterMember) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        R login =  memberService.login(ucenterMember);
        return login;
    }


    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        R register = memberService.register(registerVo);
        return register;
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo/{request}")
    public R getMemberInfo(@PathVariable String request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    @PostMapping("updata")
    public R updata(@RequestBody UcenterMember ucenterMember) {
        boolean b = memberService.updateById(ucenterMember);
        if(b) {
            return R.ok();
        }
        return R.error();
    }
}

