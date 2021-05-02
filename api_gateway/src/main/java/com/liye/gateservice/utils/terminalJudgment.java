package com.liye.gateservice.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.gateservice.utils
 * @create_time 2021/4/30 16:54
 */
public class terminalJudgment {
    public static boolean isMobileDevice(ServerHttpRequest request) {
        String requestHeader = request.getHeaders().get("User-Agent").get(0);
        String[] deviceArray = new String[]{"android", "mac os", "windows phone"};
        if (requestHeader == null) {
            return false;
        }
        requestHeader = requestHeader.toLowerCase();
        for (String device : deviceArray) {
            if (requestHeader.indexOf(device) != -1) {
                return true;
            }
        }
        return false;
    }
}
