package com.liye.gateservice.filter;

import com.google.gson.JsonObject;
import com.liye.commonutils.JwtUtils;

import com.liye.gateservice.utils.TokenManager;
import com.liye.gateservice.utils.terminalJudgment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 * 全局Filter，统一处理会员登录与外部不允许访问的服务
 * </p>
 *
 * @author qy
 * @since 2019-11-21
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {


    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private TokenManager tokenManager;
    
    
    int i= 0;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //谷粒学院api接口，校验用户必须登录
        if(antPathMatcher.match("/eduservice/**", path)||
                antPathMatcher.match("/eduoss/**", path)||
                antPathMatcher.match("/eduvod/**", path)||
                antPathMatcher.match("/educms/**", path)||
                antPathMatcher.match("/ucenterservice/edu-course-collect/**", path)) {
            String tokenList = request.getHeaders().get("token").get(0);
            System.out.println(request.getHeaders().get("token"));
            if(terminalJudgment.isMobileDevice(request)) {
                String phone = request.getHeaders().get("phone").get(0);
                String redisToken = redisTemplate.opsForValue().get(phone);
                if(null == tokenList || !tokenList.contains(redisToken)) {
                    ServerHttpResponse response = exchange.getResponse();
                    return out(response);
                }
            }else {
                String userName = tokenManager.getUserFromToken(tokenList);
                String redisToken = redisTemplate.opsForValue().get(userName);
                if(null == tokenList || redisToken==null ) {
                    ServerHttpResponse response = exchange.getResponse();
                    return out(response);
                }
            }

        }
        //内部服务接口，不允许外部访问
        if(antPathMatcher.match("/**/inner/**", path)) {
            ServerHttpResponse response = exchange.getResponse();
            return out(response);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {
        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 28004);
        message.addProperty("data", "鉴权失败");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
