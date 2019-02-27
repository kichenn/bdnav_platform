package com.bdxh.backend.configration.security.handler;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: xuyuan
 * @create: 2019-02-17 16:34
 **/
@Component
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        Wrapper wrapper= WrapMapper.error(exception.getMessage());
        String str = JSON.toJSONString(wrapper);
        response.setContentType("application/json;charset=utf-8");
        response.getOutputStream().write(str.getBytes("utf-8"));
    }

}
