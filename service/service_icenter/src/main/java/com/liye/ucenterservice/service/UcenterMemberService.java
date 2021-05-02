package com.liye.ucenterservice.service;

import com.liye.commonutils.R;
import com.liye.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liye.ucenterservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    R register(RegisterVo registerVo);

    R login(UcenterMember member);
}
