package com.liye.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.ucenterservice.entity.CourseComments;
import com.liye.ucenterservice.entity.UcenterMember;
import com.liye.ucenterservice.entity.vo.CommentsVo;
import com.liye.ucenterservice.mapper.CourseCommentsMapper;
import com.liye.ucenterservice.service.CourseCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liye.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 讨论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-13
 */
@Service
public class CourseCommentsServiceImpl extends ServiceImpl<CourseCommentsMapper, CourseComments> implements CourseCommentsService {


    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Override
    public List<CommentsVo> findAll(String id) {
        QueryWrapper<CourseComments> wrapper = new QueryWrapper<>();
        wrapper.eq("voide_id",id);
        wrapper.orderByDesc("gmt_create");
        List<CourseComments> list = baseMapper.selectList(wrapper);

        List<CommentsVo> finallList = new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            CommentsVo commentsVo = new CommentsVo();
            BeanUtils.copyProperties(list.get(i),commentsVo);
            String commentsUserId = list.get(i).getUserCommentsId();
            String replyUserId = list.get(i).getUserReplyId();
            if(!StringUtils.isEmpty(commentsUserId)) {
                UcenterMember byId = ucenterMemberService.getById(commentsUserId);
                commentsVo.setCommentsavatar(byId.getAvatar());
                commentsVo.setUserCommentsNickname(byId.getNickname());
            }

            if(!StringUtils.isEmpty(replyUserId)) {
                UcenterMember byId = ucenterMemberService.getById(replyUserId);
                commentsVo.setUserReplyNickName(byId.getNickname());
            }
            finallList.add(commentsVo);
        }
        return finallList;
    }



}
