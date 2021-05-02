package com.liye.educms.controller;

import com.liye.commonutils.R;
import com.liye.educms.entity.CsmBanner;
import com.liye.educms.service.CsmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台bannber显示
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Autowired
    private CsmBannerService bannerService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    //查询所有banner
    @Cacheable(value = "banner",key="'selectIndexList'")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CsmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

