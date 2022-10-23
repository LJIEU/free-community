package com.cjxjie.top.modules.app.handler;

import cn.hutool.json.JSONUtil;
import com.cjxjie.top.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/22 18:57
 * 拒绝访问处理程序
 */
@Slf4j
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSONUtil.parse(R.error().message(accessDeniedException.getMessage())));
        response.getWriter().flush();
        log.warn("拒绝访问错误:" + accessDeniedException.getMessage());
        log.warn("RestfulAccessDeniedHandler:\t你没有权限!");
    }
}