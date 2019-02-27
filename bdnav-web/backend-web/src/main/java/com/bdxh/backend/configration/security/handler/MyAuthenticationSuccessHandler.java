package com.bdxh.backend.configration.security.handler;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: xuyuan
 * @create: 2019-02-17 14:12
 **/
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Wrapper wrapper= WrapMapper.ok("登录成功");
        String str = JSON.toJSONString(wrapper);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
    }
}
