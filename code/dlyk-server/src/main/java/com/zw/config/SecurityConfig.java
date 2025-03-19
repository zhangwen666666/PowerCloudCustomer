package com.zw.config;

import com.zw.consts.Constant;
import com.zw.filter.TokenVerifyFilter;
import com.zw.handler.MyAuthenticationFailureHandler;
import com.zw.handler.MyAuthenticationSuccessHandler;
import com.zw.handler.MyLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private TokenVerifyFilter tokenVerifyFilter;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // 配置跨域
    public CorsConfigurationSource configurationSource() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        // 跨域配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*")); // 允许任何来源 也可以指定只允许http://localhost:8888访问
        corsConfiguration.setAllowedMethods(List.of("*")); // 允许任何请求方式
        corsConfiguration.setAllowedHeaders(List.of("*")); // 允许任何的请求头

        // 注册跨域配置
        // 以下的/**表示任何路径，即后端的所有路径都使用corsConfiguration配置
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource configurationSource) throws Exception {
        // 禁用跨站伪造请求
        return httpSecurity
                .formLogin(formLogin -> {
                    formLogin.loginProcessingUrl(Constant.LOGIN_URI) // 登录处理地址，不需要写controller
                            .usernameParameter("loginAct")
                            .passwordParameter("loginPwd")
                            .successHandler(myAuthenticationSuccessHandler)
                            .failureHandler(myAuthenticationFailureHandler);
                })
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/login").permitAll()
                            .anyRequest().authenticated(); // 任何请求都需要登录后才能访问
                })
                .csrf(AbstractHttpConfigurer::disable) // 禁用跨站伪造检测
                .cors((cors) -> {
                    cors.configurationSource(configurationSource);
                })
                // 禁用session
                .sessionManagement(t -> {
                    // 创建session采用无状态策略，即不创建session
                    t.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(tokenVerifyFilter, LogoutFilter.class) // 添加token验证过滤器
                .logout((logout) -> {
                    logout.logoutUrl("/api/logout").logoutSuccessHandler(myLogoutSuccessHandler);
                })
                .build();
    }
}
