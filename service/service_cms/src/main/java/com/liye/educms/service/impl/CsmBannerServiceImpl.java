package com.liye.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liye.educms.entity.CsmBanner;
import com.liye.educms.mapper.CsmBannerMapper;
import com.liye.educms.service.CsmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-15
 */
@Service
public class CsmBannerServiceImpl extends ServiceImpl<CsmBannerMapper, CsmBanner> implements CsmBannerService {

    @Override
    public List<CsmBanner> selectAllBanner() {
        //根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<CsmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接sql语句
        wrapper.last("limit 2");
        List<CsmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
