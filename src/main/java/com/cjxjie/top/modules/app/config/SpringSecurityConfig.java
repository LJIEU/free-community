package com.cjxjie.top.modules.app.config;

import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.filter.JwtAuthenticationTokenFilter;
import com.cjxjie.top.modules.app.handler.RestAuthenticationEntryPoint;
import com.cjxjie.top.modules.app.handler.RestfulAccessDeniedHandler;
import com.cjxjie.top.modules.app.service.UserService;
import com.cjxjie.top.modules.app.details.TempDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/22 18:54
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Lazy // 设置懒加载
    @Autowired
    private UserService userService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

//    UsernamePasswordAuthenticationFilter // 用户名和密码认证拦截器


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 跨域攻击防护【关闭】
                .cors(Customizer.withDefaults()).csrf().disable()
                // 同源打开iframe
                .headers().frameOptions().sameOrigin()
                .and()
                // 允许通过请求
                .authorizeRequests()
                .antMatchers("/sys/**", "/verification-code.jpg").permitAll()
                .antMatchers("/show/**").permitAll()
                .antMatchers("/es/**").permitAll()
                .antMatchers("/",
                        "/swagger-ui.html",
                        "/webjars/**", "/druid/**", "/swagger-resources/**", "/v2/**").permitAll()

                .antMatchers("/app/api/login").anonymous() // 登录
                .antMatchers("/app/api/verify").anonymous() // 验证
                .antMatchers("/app/api/getVerifyCode/**").anonymous() // 获取验证码
                .antMatchers("/app/api/register/**").anonymous() // 注册
                .antMatchers("/app/api/user/**").anonymous()
                .antMatchers("/app/api/postList/**").anonymous()
                .antMatchers("/app/api/topicList/**").anonymous()
                .antMatchers("/app/api/post/**").anonymous()
                .antMatchers("/app/api/comment/list/**").anonymous()
                .antMatchers("/app/admin/**").anonymous()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // 其他接口 都需要JWT认证 才可以访问
                .anyRequest().authenticated()
                .and()
                // 不创建 session 会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http.headers().cacheControl();
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
            UserEntity user = userService.getByUsername(username);
            if (user != null) {
                return new TempDetails(user);
            }
            // 未找到用户名异常 需要将 hideUserNotFoundExceptions 设置为 false 才有效果
            throw new UsernameNotFoundException("未该用户,登录失败!");
        };
    }

    /**
     * 登录过滤器
     */
    @Bean
    public Filter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    /**
     * 身份验证
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                // 由于使用的是JWT，我们这里不需要csrf
//                .csrf().disable()
//                .sessionManagement()// 基于token，所以不需要session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js",
//                        "/swagger-resources/**",
//                        "/v2/api-docs/**",
//                        "/swagger-ui.html"
//                )
//                .permitAll()
//                .antMatchers("/app/login", "/app/register")// 对登录注册要允许匿名访问
//                .permitAll()
//                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
//                .permitAll()
////                .antMatchers("/**")//测试时全部运行访问
////                .permitAll()
//                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
//                .authenticated()
//        ;
//        // 禁用缓存
//        http.headers().cacheControl();
//        // 添加JWT filter
//        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        //添加自定义未授权和未登录结果返回
//        http.exceptionHandling() // 异常处理
//                .accessDeniedHandler(restfulAccessDeniedHandler) // 访问拒绝处理
//                .authenticationEntryPoint(restAuthenticationEntryPoint); // 登录失败处理
//        // 防止web页面被Frame拦截
//        http.headers().frameOptions().disable();
//    }