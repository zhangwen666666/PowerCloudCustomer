package com.zw.filter;

import com.zw.consts.Constant;
import com.zw.entity.TUser;
import com.zw.result.R;
import com.zw.util.JSONUtils;
import com.zw.util.JwtUtil;
import com.zw.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenVerifyFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (Constant.LOGIN_URI.equals(requestURI)){
            // 登录请求，此时没有生成jwt，不需要验证
            filterChain.doFilter(request, response);
            return;
        }
        String authorization = request.getHeader("Authorization"); // 前端携带的jwt
        if (!StringUtils.hasText(authorization)){
            // 前端携带的jwt为空
            R<String> result = R.error(901,"token为空");
            String json = JSONUtils.toJSON(result);
            ResponseUtil.write(response, json);
            return;
        }
        if (!JwtUtil.verifyToken(authorization)) {
            // 验证不通过
            R<String> result = R.error(902, "token验证失败");
            String json = JSONUtils.toJSON(result);
            ResponseUtil.write(response, json);
            return;
        }
        // token验证通过, 检查token是否过期
        TUser user = JwtUtil.parseToken(authorization);
        String redisJwt = (String) redisTemplate.opsForValue().get(Constant.REDIS_TOKEN_KEY + user.getId());
        if (!StringUtils.hasText(redisJwt)){
            // token过期
            R<String> result = R.error(903, "token过期，请重新登录");
            String json = JSONUtils.toJSON(result);
            ResponseUtil.write(response, json);
            return;
        }
        // 检查前端传过来的token和redis中的是否相等
        if (!redisJwt.equals(authorization)) {
            // 不相等
            R<String> result = R.error(904, "token不匹配");
            String json = JSONUtils.toJSON(result);
            ResponseUtil.write(response, json);
            return;
        }
        // jwt验证成功, token验证通过之后，要在SpringSecurity的上下文中放置一个认证对象
        // 这样，框架在执行后序的Filter的时候，才知道是登录的
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.NO_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
