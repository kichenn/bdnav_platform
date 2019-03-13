package com.bdxh.weixiao.configration.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: xuyuan
 * @create: 2019-02-28 14:21
 **/
@Component
@Slf4j
public class MyAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("1");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        System.out.println("2");
    }

}
