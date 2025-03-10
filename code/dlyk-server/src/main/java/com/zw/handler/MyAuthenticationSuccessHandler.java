package com.zw.handler;


import com.zw.consts.Constant;
import com.zw.entity.TUser;
import com.zw.result.R;
import com.zw.util.JSONUtils;
import com.zw.util.JwtUtil;
import com.zw.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功会执行该Handler
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TUser user = (TUser) authentication.getPrincipal();
        // 生成JWT
        String jwt = JwtUtil.createJWT(user);

        // 写入redis (tokenKey + userId, jwt)
        redisTemplate.opsForValue().set(Constant.REDIS_TOKEN_KEY + user.getId(), jwt);
        // 设置jwt过期时间 （如果选择了记住我，过期时间是7天，否则是30分钟）
        String rememberMe = request.getParameter("rememberMe");
        if (Boolean.parseBoolean(rememberMe)){
            // 设置7天
            redisTemplate.expire(Constant.REDIS_TOKEN_KEY + user.getId(), Constant.EXPIRE_TIME, TimeUnit.SECONDS);
        } else {
            // 设置30分钟
            redisTemplate.expire(Constant.REDIS_TOKEN_KEY + user.getId(), Constant.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        // 封装返回数据
        R<String> result = R.OK(jwt);
        String jsonStr = JSONUtils.toJSON(result);
        // 把json相应给客户端
        ResponseUtil.write(response, jsonStr);
    }
}
