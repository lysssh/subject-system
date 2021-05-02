package com.liye.educms.service;

import com.liye.educms.entity.CsmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-15
 */
public interface CsmBannerService extends IService<CsmBanner> {

    List<CsmBanner> selectAllBanner();
}
