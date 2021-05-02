package com.liye.eduservice.client.clientImpl;

import com.liye.commonutils.R;
import com.liye.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.eduservice.client.clientImpl
 * @create_time 2021/3/13 20:42
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R removeById(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R removeByCourseId(List<String> videoIdList) {
        return R.error().message("删除视频出错");
    }
}
