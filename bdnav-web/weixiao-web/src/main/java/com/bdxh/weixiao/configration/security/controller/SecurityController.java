package com.bdxh.weixiao.configration.security.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.bdxh.common.utils.HttpClientUtils;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: xuyuan
 * @create: 2019-02-28 10:47
 **/
@RestController
@RequestMapping("/authentication")
@Slf4j
public class SecurityController {

    private static final String TOKEN_URL = "https://weixiao.qq.com/apps/school-auth/access-token";

    private static final String USER_INFO_URL = "https://weixiao.qq.com/apps/school-auth/user-info";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login() throws IOException {
        try {
            System.out.println("sss");
/*            Preconditions.checkArgument(StringUtils.isNotEmpty(schoolCode),"学校编码不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(wxcode),"微信授权码不能为空");
            //根据学校编码查询学校信息
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("","");
            jsonObject.put("","");
            jsonObject.put("","");
            String tokenResponseStr = HttpClientUtils.doPostJson(TOKEN_URL, jsonObject.toJSONString());
            jsonObject = JSON.parseObject(tokenResponseStr);
            String accessToken = jsonObject.getString("access_token");
            //通过token获取用户信息
            jsonObject.clear();
            jsonObject.put("token",accessToken);
            String userInfoResponseStr = HttpClientUtils.doPostJson(USER_INFO_URL, jsonObject.toJSONString());
            jsonObject = JSON.parseObject(userInfoResponseStr);
            //组装用户信息放入
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(200);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write("测试".getBytes("utf-8"));*/
        } catch (Exception e) {
/*            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(401);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write("测试".getBytes("utf-8"));*/
        }
    }

}
