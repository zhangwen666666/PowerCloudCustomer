package com.zw.handler;

import cn.hutool.json.JSONUtil;
import com.zw.result.R;
import com.zw.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 返回json，告诉前端，权限不足
        R<Object> result = R.builder().code(403).msg("权限不足").build();
        String jsonStr = JSONUtil.toJsonStr(result);
        // 把json写出给前端
        ResponseUtil.write(response, jsonStr);
    }
}
