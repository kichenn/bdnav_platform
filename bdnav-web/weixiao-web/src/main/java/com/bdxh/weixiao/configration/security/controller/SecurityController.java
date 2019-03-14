package com.bdxh.weixiao.configration.security.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.utils.HttpClientUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.properties.SecurityConstant;
import com.google.common.base.Preconditions;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    public void login(@RequestParam("schoolCode") String schoolCode, @RequestParam("wxcode") String wxcode, HttpServletResponse response) throws IOException {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(schoolCode),"学校编码不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(wxcode),"微信授权码不能为空");
            //根据学校编码查询学校信息
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("app_key", SecurityConstant.PROVIDER_APP_KEY);
            jsonObject.put("app_secret",SecurityConstant.PROVIDER_APP_SECRET);
            jsonObject.put("wxcode",wxcode);
            String tokenResponseStr = HttpClientUtils.doPostJson(TOKEN_URL, jsonObject.toJSONString());
            jsonObject = JSON.parseObject(tokenResponseStr);
            String accessToken = jsonObject.getString("access_token");
            Preconditions.checkArgument(StringUtils.isNotEmpty(accessToken),"获取微信token失败");
            //通过token获取用户信息
            jsonObject.clear();
            jsonObject.put("token",accessToken);
            String userInfoResponseStr = HttpClientUtils.doPostJson(USER_INFO_URL, jsonObject.toJSONString());
            jsonObject = JSON.parseObject(userInfoResponseStr);
            String resultCode = jsonObject.getString("errcode");
            Preconditions.checkArgument(StringUtils.equals(resultCode,"0"),"获取用户信息失败");
            //组装用户信息放入
            UserInfo userInfo = new UserInfo();
            userInfo.setSchoolCode(schoolCode);
            userInfo.setName(jsonObject.getString("name"));
            userInfo.setCardNumber(jsonObject.getString("card_number"));
            userInfo.setPhone(jsonObject.getString("telephone"));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(schoolCode).append("_").append(userInfo.getName()).append("_").append(userInfo.getCardNumber()).append("_").append(userInfo.getPhone());
            //生成token
            String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(stringBuilder.toString())
                    .claim(SecurityConstant.USER_INFO,stringBuilder.toString())
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.TOKEN_EXPIRE_TIME))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                    .compressWith(CompressionCodecs.GZIP).compact();
            //将token存入redis
            redisTemplate.opsForValue().set(SecurityConstant.TOKEN_KEY+stringBuilder.toString(),token,SecurityConstant.TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
            Wrapper<String> wrapper = WrapMapper.ok(token);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(200);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(JSON.toJSONString(wrapper).getBytes("utf-8"));
        } catch (Exception e) {
            Wrapper<String> wrapper = WrapMapper.error(e.getMessage());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(500);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(JSON.toJSONString(wrapper).getBytes("utf-8"));
        }
    }

}
