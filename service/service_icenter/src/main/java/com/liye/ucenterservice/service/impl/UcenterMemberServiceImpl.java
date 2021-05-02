package com.liye.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.commonutils.JwtUtils;
import com.liye.commonutils.R;
import com.liye.servicebase.exceptionhandler.SubjectSystemException;
import com.liye.ucenterservice.entity.UcenterMember;
import com.liye.ucenterservice.entity.vo.RegisterVo;
import com.liye.ucenterservice.mapper.UcenterMemberMapper;
import com.liye.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liye.ucenterservice.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-15
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {



    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public R register(RegisterVo registerVo) {
        //获取注册的数据
        //String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(nickname)) {
            throw new SubjectSystemException(20001,"注册失败");
        }
        //判断验证码
        //获取redis验证码
//        String redisCode = redisTemplate.opsForValue().get(mobile);
//        if(!code.equals(redisCode)) {
//            throw new SubjectSystemException(20001,"注册失败");
    //}

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            return R.error().data("err","该手机号以注册");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setSex(registerVo.getSex());
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://edu-gulioarent.oss-cn-beijing.aliyuncs.com/%E6%95%99%E5%B8%88%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpeg");
        baseMapper.insert(member);
        return R.ok().data("success","注册成功").data("err",null);
    }

    @Override
    public R login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new SubjectSystemException(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(mobileMember == null) {//没有这个手机号
            return R.error().data("err","该账号未注册");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            return R.error().data("err","密码错误");
        }

        //判断用户是否禁用
        if(mobileMember.getIsDisabled()) {
            return R.error().data("err","您被禁止登录");

        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        redisTemplate.opsForValue().set(mobile,jwtToken,5, TimeUnit.MINUTES);
        return R.ok().data("token",jwtToken);
    }
}
