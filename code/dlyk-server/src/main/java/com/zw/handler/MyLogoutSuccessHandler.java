package com.zw.handler;

import cn.hutool.json.JSONUtil;
import com.zw.consts.Constant;
import com.zw.entity.TUser;
import com.zw.result.R;
import com.zw.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 退出成功之后执行该Handler
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 退出登录，只能删除自己的token
        Integer userId = ((TUser) authentication.getPrincipal()).getId();
        redisTemplate.delete(Constant.REDIS_TOKEN_KEY + userId);

        R result = R.OK("退出成功", null);
        String jsonStr = JSONUtil.toJsonStr(result);
        ResponseUtil.write(response, jsonStr);
    }
}
