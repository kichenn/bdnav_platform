package com.bdxh.weixiao.configration.security.handler;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Description:   
* @Author: Kang
* @Date: 2019/5/10 15:03
*/
@Component
@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException exception) throws IOException, ServletException {
        Wrapper wrapper= WrapMapper.error("暂无操作权限");
        String str = JSON.toJSONString(wrapper);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setStatus(403);
        httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
    }

}
