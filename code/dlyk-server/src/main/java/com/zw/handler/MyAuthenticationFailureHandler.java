package com.zw.handler;

import cn.hutool.json.JSONUtil;
import com.zw.result.R;
import com.zw.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登录失败会执行该Handler
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        R result = R.error("登录失败：" + exception.getMessage());

        String jsonStr = JSONUtil.toJsonStr(result);
        ResponseUtil.write(response, jsonStr);
    }
}
