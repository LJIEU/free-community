package com.cjxjie.top.modules.sys.oauth2;

import cn.hutool.http.HttpStatus;
import com.cjxjie.top.common.utils.HttpContextUtils;
import com.cjxjie.top.common.utils.R;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/06 22:09
 * <p>
 * 过滤器
 */
// 继承认证过滤器【AuthenticatingFilter】  别弄成 身份验证过滤器【AuthenticationFilter】
@Slf4j
public class OAuth2Filter extends AuthenticatingFilter {


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new OAuth2Token(token);
    }

    /**
     * 获取 token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        // 从 header 中获取 token
        String token = httpRequest.getHeader("token");

        // 如果 header 中不存在 则在参数中获取 token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }
        return token;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        /*
        OPTIONS:请求它用于获取当前URL所支持的方法。若请求成功，则它会在HTTP头中包含一个名为“Allow”的头，值是所支持的方法，如“GET, POST”
         */
        return httpRequest.getMethod().equals(RequestMethod.OPTIONS.name());
    }

    /**
     * 拒绝访问
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.error("拒绝访问无有效Token~~~");
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

            // 转 JSON 数据
            String json = new Gson().toJson(R.error(HttpStatus.HTTP_UNAUTHORIZED, "无效Token【invalid token】"));
            httpResponse.getWriter().print(json);
            return false; // 禁止放行
        }
        return executeLogin(request, response);
    }

    /**
     * 登录失败
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("登录失败无有效Token~~~");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();

            String json = new Gson().toJson(R.error(HttpStatus.HTTP_UNAUTHORIZED, throwable.getMessage()));
            httpResponse.getWriter().print(json);
        } catch (IOException ignored) {

        }
        return false;
    }
}
