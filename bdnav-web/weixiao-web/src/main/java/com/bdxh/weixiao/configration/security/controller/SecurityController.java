package com.bdxh.weixiao.configration.security.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.Account;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.HttpClientUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.weixiao.configration.redis.RedisUtil;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.properties.SecurityConstant;
import com.bdxh.weixiao.configration.security.properties.weixiao.WeixiaoLoginConstant;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.google.common.base.Preconditions;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 腾讯微校认证信息
 * @Author: Kang
 * @Date: 2019/5/14 14:42
 */
@RestController
@RequestMapping
@Api(value = "微校认证相关", tags = "微校认证交互API")
@Slf4j
public class SecurityController {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @param schoolCode 学校code
     * @param wxcode     微信授权码
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/authenticationWeixiao/login", method = RequestMethod.GET)
    @ApiOperation(value = "获取token(微校授权登录)", response = String.class)
    public void login(@RequestParam("schoolCode") String schoolCode, @RequestParam("wxcode") String wxcode, HttpServletResponse response) throws IOException {
        try {
            //根据学校编码查询学校信息暂时不查询数据库
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("app_key", WeixiaoLoginConstant.appKey);
            jsonObject.put("app_secret", WeixiaoLoginConstant.appSecret);
            jsonObject.put("wxcode", wxcode);
            //发起请求获取access_token
            String tokenResponseStr = HttpClientUtils.doPostJson(WeixiaoLoginConstant.TOKEN_URL, jsonObject.toJSONString());
            if (StringUtils.isNotEmpty(tokenResponseStr)) {
                tokenResponseStr = StringEscapeUtils.unescapeJava(tokenResponseStr);
            }
            jsonObject = JSON.parseObject(tokenResponseStr);
            String accessToken = jsonObject.getString("access_token");
            Preconditions.checkArgument(StringUtils.isNotEmpty(accessToken), "获取微信token失败");
            //通过token获取用户信息
            jsonObject.clear();
            jsonObject.put("token", accessToken);
            //通过access_token获取用户信息
            String userInfoResponseStr = HttpClientUtils.doPostJson(WeixiaoLoginConstant.USER_INFO_URL, jsonObject.toJSONString());
            if (StringUtils.isNotEmpty(userInfoResponseStr)) {
                userInfoResponseStr = StringEscapeUtils.unescapeJava(userInfoResponseStr);
            }
            jsonObject = JSON.parseObject(userInfoResponseStr);
            String resultCode = jsonObject.getString("errcode");
            Preconditions.checkArgument(StringUtils.equals(resultCode, "0"), "获取用户信息失败");
            //组装用户信息放入
            UserInfo userInfo = new UserInfo();
            userInfo.setSchoolCode(schoolCode);
            userInfo.setName(jsonObject.getString("name"));
            userInfo.setCardNumber(jsonObject.getString("card_number"));
            userInfo.setWeixiaoStuId(jsonObject.getString("weixiao_stu_id"));
            userInfo.setPhone(jsonObject.getString("telephone"));
            userInfo.setIdentityType(jsonObject.getString("identity_type"));

            String subject = userInfo.getWeixiaoStuId();
            String claim = JSONObject.toJSONString(userInfo);
            //生成token
            String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(subject)
                    .claim(SecurityConstant.USER_INFO, claim)
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.TOKEN_EXPIRE_TIME * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                    .compressWith(CompressionCodecs.GZIP).compact();
            //将token存入redis(单位秒。)
            redisUtil.setWithExpireTime(SecurityConstant.TOKEN_KEY + subject, token, SecurityConstant.TOKEN_EXPIRE_TIME * 60);
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


    @GetMapping("/getWeixiaoUserInfoByToken")
    @ApiOperation(value = "token获取用户信息", response = String.class)
    public Object getWeixiaoUserInfoByToken() {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        return WrapMapper.ok(userInfo);
    }

    @GetMapping("/getTokenTime")
    @ApiOperation(value = "获取wtoken的有效时间", response = String.class)
    public Object getTokenTime(@RequestParam(name = "loseToken") String loseToken) {
        String authHeader = loseToken;
        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            String auth = authHeader.substring(SecurityConstant.TOKEN_SPLIT.length());
            try {
                Claims claims = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(auth).getBody();
                String resultDate = DateUtil.format(claims.getExpiration(), "yyyy-MM-dd HH:mm:ss");
                return WrapMapper.ok(resultDate);
            } catch (ExpiredJwtException e) {
                return WrapMapper.ok(e.getClaims().getExpiration());
            }
        }
        return WrapMapper.error();
    }
}
