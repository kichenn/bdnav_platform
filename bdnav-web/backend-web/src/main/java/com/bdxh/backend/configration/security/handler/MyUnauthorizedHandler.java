package com.bdxh.backend.configration.security.handler;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Kang
 * @create: 2019-02-28 11:44
 **/
@Component
public class MyUnauthorizedHandler implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException {
        Wrapper wrapper=WrapMapper.wrap(401,"未登录系统");
        String str = JSON.toJSONString(wrapper);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
    }

}
