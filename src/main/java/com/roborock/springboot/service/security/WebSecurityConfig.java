package com.roborock.springboot.service.security;

import com.roborock.springboot.service.security.filter.JwtAuthenticationTokenFilter;
import com.roborock.springboot.service.security.handler.CustomizeAccessDeniedHandler;
import com.roborock.springboot.service.security.handler.CustomizeAuthenticationFailureHandler;
import com.roborock.springboot.service.security.handler.CustomizeAuthenticationSuccessHandler;
import com.roborock.springboot.service.security.handler.CustomizeLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //登录成功处理逻辑
    @Autowired
    private CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理逻辑
    @Autowired
    private CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    //权限拒绝处理逻辑
    @Autowired
    private CustomizeAccessDeniedHandler accessDeniedHandler;

    //匿名用户访问无权限资源时的异常
    @Autowired
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    //登出成功处理逻辑
    @Autowired
    private CustomizeLogoutSuccessHandler logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
/*    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }*/

    @Bean
    public UserDetailsService userDetailsService() {
        //获取用户账号密码及权限信息
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置认证方式
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http相关的配置，包括登入登出、异常处理、会话管理等
        http.csrf().disable();
        http// 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/login", "/captchaImage").anonymous().anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        // 登出
        http.logout().permitAll().// 允许所有用户
                logoutSuccessHandler(logoutSuccessHandler)// 登出成功处理逻辑
                .and()// 登入
                .formLogin().permitAll()// 允许所有用户
                .successHandler(authenticationSuccessHandler)// 登录成功处理逻辑
                .failureHandler(authenticationFailureHandler)// 登录失败处理逻辑
                //异常处理(权限拒绝、登录失效等)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)// 权限拒绝处理逻辑
                .authenticationEntryPoint(authenticationEntryPoint);// 匿名用户访问无权限资源时的异常处理
        // 添加JWT filter
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        http.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(corsFilter, LogoutFilter.class);
    }

}
