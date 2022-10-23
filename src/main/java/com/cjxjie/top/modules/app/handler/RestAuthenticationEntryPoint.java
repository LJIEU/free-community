package com.cjxjie.top.modules.app.handler;

import cn.hutool.json.JSONUtil;
import com.cjxjie.top.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/22 18:57
 * 身份验证入口点
 */
@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(R.error().message(authException.getMessage())));
        response.getWriter().flush();
        log.warn("身份验证错误:" + authException.getMessage());
        log.warn("RestAuthenticationEntryPoint:\tToken失效,未登录成功");
    }
}
