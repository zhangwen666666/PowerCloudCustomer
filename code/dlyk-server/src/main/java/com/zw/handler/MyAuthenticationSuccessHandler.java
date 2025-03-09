package com.zw.handler;


import com.zw.entity.TUser;
import com.zw.result.R;
import com.zw.util.JSONUtils;
import com.zw.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登录成功会执行该Handler
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 生成JWT
        TUser user = (TUser) authentication.getPrincipal();

        // 封装返回数据
        R<TUser> result = R.OK(user);
        String jsonStr = JSONUtils.toJSON(result);
        // 把json相应给客户端
        ResponseUtil.write(response, jsonStr);
    }
}
